package com.yhh.practice.netty.StickExcrete.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class ClientHandler2 extends SimpleChannelInboundHandler<ByteBuf> {
    private  AtomicInteger atomicInteger = new AtomicInteger(0);
    public static final String DELIMITER_SYMBOL = "@~"; //自定义分隔符
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("client Accept["+byteBuf.toString(CharsetUtil.UTF_8)
                +"] and the counter is:"+atomicInteger.incrementAndGet());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        String st = "测试粘包拆包"+Config.DELIMITER_SYMBOL;
//        ByteBuf byteBuf =null;
//        for (int i = 0; i <100 ; i++) {
//            //多次发送数据包
//            byteBuf = Unpooled.buffer(st.length());
//            byteBuf.writeBytes(st.getBytes());
//            ctx.writeAndFlush(byteBuf);
//            System.out.println("客户端发送数据包次数  num:   "+atomicInteger.incrementAndGet());
//        }
//        System.out.println("客户端发送数据包总次数   count:   "+atomicInteger.get());

        ByteBuf msg = null;
        String request = "测试粘包拆包"
                +  Config.DELIMITER_SYMBOL;
        for(int i=0;i<100;i++){
            msg = Unpooled.buffer(request.length());
            msg.writeBytes(request.getBytes());
            ctx.writeAndFlush(msg);
            System.out.println("客户端发送数据包次数  num:   "+atomicInteger.incrementAndGet());
        }
        System.out.println("客户端发送数据包总次数   count:   "+atomicInteger.get());
    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(Unpooled.copiedBuffer("hellow  channelReadComplete ", CharsetUtil.UTF_8))
        .addListener(ChannelFutureListener.CLOSE);
    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

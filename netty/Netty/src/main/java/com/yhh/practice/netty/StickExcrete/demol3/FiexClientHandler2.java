package com.yhh.practice.netty.StickExcrete.demol3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

public class FiexClientHandler2 extends SimpleChannelInboundHandler<ByteBuf> {
    private  AtomicInteger atomicInteger = new AtomicInteger(0);
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf) throws Exception {
        System.out.println("client Accept["+byteBuf.toString(CharsetUtil.UTF_8)
                +"] and the counter is:"+atomicInteger.incrementAndGet());
    }
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        //String st = "测试粘包拆包";
        ByteBuf byteBuf =null;
        for (int i = 0; i <100000 ; i++) {
            //多次发送数据包
            byteBuf = Unpooled.buffer(Constant.REQUEST.length());
            byteBuf.writeBytes(Constant.REQUEST.getBytes());
            ctx.writeAndFlush(byteBuf);
            System.out.println("客户端发送数据包次数  num:   "+atomicInteger.incrementAndGet());
        }
        System.out.println("客户端发送数据包总次数   count:   "+atomicInteger.get());
    }
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush(Unpooled.copiedBuffer("hellow  channelReadComplete ", CharsetUtil.UTF_8))
//        .addListener(ChannelFutureListener.CLOSE);
//        System.out.println("channelReadComplete");
//    }
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

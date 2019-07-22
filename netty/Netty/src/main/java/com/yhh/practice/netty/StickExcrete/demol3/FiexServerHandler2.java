package com.yhh.practice.netty.StickExcrete.demol3;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.util.concurrent.atomic.AtomicInteger;

/*指明我这个handler可以在多个channel之间共享，意味这个实现必须线程安全的。*/
@ChannelHandler.Sharable
public class FiexServerHandler2 extends ChannelInboundHandlerAdapter {
    private  AtomicInteger atomicInteger = new AtomicInteger();
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
        String request = in.toString(CharsetUtil.UTF_8);
        System.out.println("Server Accept["+request
                +"] and the counter is:"+atomicInteger.incrementAndGet());
            ctx.writeAndFlush(
                Unpooled.copiedBuffer(Constant.REQUEST.getBytes()));

    }
//    @Override
//    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
//        /*flush掉所有的数据*/
//        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);/*当flush完成后，关闭连接*/
//    }

//    @Override
//    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        ctx.writeAndFlush("hellow  netty yhh");
//        ctx.fireChannelActive();
//    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

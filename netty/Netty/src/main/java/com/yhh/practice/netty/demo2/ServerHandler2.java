package com.yhh.practice.netty.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

import java.util.concurrent.atomic.AtomicInteger;

/*指明我这个handler可以在多个channel之间共享，意味这个实现必须线程安全的。*/
@ChannelHandler.Sharable
public class ServerHandler2 extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf) msg;
        String st = " this is response  ";
        System.out.println("Server Accept:"+in.toString(CharsetUtil.UTF_8));
        in.writeBytes(st.getBytes());
        ctx.writeAndFlush(in);
        ByteBufAllocator allocator =ctx.alloc();
        allocator.buffer();
        ctx.channel().alloc();
        ctx.fireChannelRead(msg);
        ByteBuf byteBuf =  Unpooled.buffer();

    }
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        /*flush掉所有的数据*/
        ctx.writeAndFlush(Unpooled.EMPTY_BUFFER).addListener(ChannelFutureListener.CLOSE);/*当flush完成后，关闭连接*/
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush("hellow  netty yhh");
        ctx.fireChannelActive();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

package com.yhh.practice.netty.StickExcrete.demo2;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.net.InetSocketAddress;

public class Server {
    public static final String DELIMITER_SYMBOL = "@~"; //自定义分隔符
    private int host;

    public Server(int host) {
        this.host = host;
    }

    public  void start(int port){
        //线程组
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try{
            //服务端启动必备
            ServerBootstrap b = new ServerBootstrap();
            b.group(eventLoopGroup)
                    /*指明使用NIO进行网络通讯*/
                    .channel(NioServerSocketChannel.class)
                    //指明服务器监听端口
                    .localAddress(new InetSocketAddress(port))
                    //
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializerImpl());
            ChannelFuture f = b.bind().sync();
            /*阻塞，直到channel关闭*/
            f.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ;
        }
    }
    private static class ChannelInitializerImpl extends ChannelInitializer<Channel>{

        @Override
        protected void initChannel(Channel channel) throws Exception {
            ByteBuf byteBuf = Unpooled.copiedBuffer(DELIMITER_SYMBOL.getBytes());
            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024,byteBuf));
            channel.pipeline().addLast(new ServerHandler2());
        }
    }

    public static void main(String[] args) {
        Server server = new Server(6666);
        server.start(6666);

    }
}

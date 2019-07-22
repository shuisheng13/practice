package com.yhh.practice.netty.StickExcrete.demol3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class FiexServer {
    private int host;

    public FiexServer(int host) {
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
                    .childHandler(new ChannelInitializerImp());
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
    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            //消息定长
            ch.pipeline().addLast(
                    new FixedLengthFrameDecoder(
                            Constant.REQUEST.length()));
            ch.pipeline().addLast(new FiexServerHandler2());
        }
    }
    public static void main(String[] args) {
        FiexServer server = new FiexServer(6666);
        System.out.println("服务器即将启动");
        server.start(6666);

    }
}

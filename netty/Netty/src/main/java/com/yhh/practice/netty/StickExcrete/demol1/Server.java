package com.yhh.practice.netty.StickExcrete.demol1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

public class Server {

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
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new ServerHandler2());
                        }
                    });
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

    public static void main(String[] args) {
        Server server = new Server(6666);
        server.start(6666);

    }
}

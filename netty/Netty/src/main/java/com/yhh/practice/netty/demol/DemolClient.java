package com.yhh.practice.netty.demol;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class DemolClient {

    private final   String host;
    private final int port;

    public DemolClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    private void start(){
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        try {

            Bootstrap b = new Bootstrap();
            b.group(eventLoopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ClientHandler());
            ChannelFuture channelFuture = b.connect().sync();       //阻塞连接  直到连接完成
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            try {
                eventLoopGroup.shutdownGracefully().sync();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new DemolClient("127.0.0.1",9999).start();
    }
}

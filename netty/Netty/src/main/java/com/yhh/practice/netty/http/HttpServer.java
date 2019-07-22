package com.yhh.practice.netty.http;

import com.yhh.practice.netty.StickExcrete.demol1.Server;
import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

public class HttpServer {
    public static final int port =6788;
    private static EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
    private static ServerBootstrap bootstrap = new ServerBootstrap();
    private static final boolean SSL = false;


    public static void main(String[] args) {
        final SslContext sslCtx;
        try{
            if (SSL) {
                //netty为我们提供的ssl加密，缺省
                SelfSignedCertificate ssc = new SelfSignedCertificate();
                sslCtx = SslContextBuilder.forServer(ssc.certificate(),
                        ssc.privateKey()).build();
            } else {
                sslCtx = null;
            }

            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new HttpServerHandler(sslCtx));
            ChannelFuture channelFuture = bootstrap.bind(port).sync();
            System.out.println("服务端已启动   端口 ： "+port);
            channelFuture.channel().closeFuture().sync();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            // do nothing
            eventLoopGroup.shutdownGracefully();
        }
    }

}

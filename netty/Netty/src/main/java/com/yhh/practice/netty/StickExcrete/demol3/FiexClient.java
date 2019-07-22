package com.yhh.practice.netty.StickExcrete.demol3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;

import java.net.InetSocketAddress;

public class FiexClient {
    public static final String RESPONSE = "Welcome to Netty!";
    private String host;
    private int port;

    public FiexClient(String host, int port) {
        this.host = host;
        this.port = port;
    }
    public static void start(String host,int port){

        EventLoopGroup e = new NioEventLoopGroup();
        try{
            Bootstrap b = new Bootstrap();
            b.group(e)
                    .channel(NioSocketChannel.class) /*指明使用NIO进行网络通讯*/
                    .remoteAddress(new InetSocketAddress(host,port)) /*配置远程服务器的地址*/
                    .handler(new ChannelInitializerImp());
            ChannelFuture f = b.connect().sync(); /*连接到远程节点，阻塞等待直到连接完成*/
            /*阻塞，直到channel关闭*/
            f.channel().closeFuture().sync();
        }catch (Exception e1){
            e1.printStackTrace();
        }finally {
            try {
                e.shutdownGracefully().sync();
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
    }
    private static class ChannelInitializerImp extends ChannelInitializer<Channel> {

        @Override
        protected void initChannel(Channel ch) throws Exception {
            //消息定长
            ch.pipeline().addLast(
                    new FixedLengthFrameDecoder(
                            Constant.REQUEST.length()));
            ch.pipeline().addLast(new FiexClientHandler2());
        }
    }

    public static void main(String[] args) {

        FiexClient client = new FiexClient("127.0.0.1",6666);
        client.start("127.0.0.1",6666);

    }



}

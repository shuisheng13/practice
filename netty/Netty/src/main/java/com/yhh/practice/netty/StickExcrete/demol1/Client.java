package com.yhh.practice.netty.StickExcrete.demol1;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

public class Client {
    private String host;
    private int port;

    public Client(String host, int port) {
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
                    .handler(new ClientHandler2());
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

    public static void main(String[] args) {

        Client client = new Client("127.0.0.1",6666);
        client.start("127.0.0.1",6666);

    }



}

package com.yhh.practice.netty.server;

public class NettyServer {

    private static NettyServerHandler nettyServerHandler;

    public static void start(){
        if(null!=nettyServerHandler){
            nettyServerHandler.stop();
        }else{
            nettyServerHandler = new NettyServerHandler(12345);
            new Thread(nettyServerHandler,"Server").start();
        }
    }

    public static void main(String[] args) {
        start();
    }


}

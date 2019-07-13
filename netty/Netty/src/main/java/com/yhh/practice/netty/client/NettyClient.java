package com.yhh.practice.netty.client;

import java.io.IOException;
import java.util.Scanner;

public class NettyClient {
    private static final String DEFAULT_SERVER_IP = "127.0.0.1";
    private static NioClientHandler nioClientHandler;

    private static void start(){
        if(null!=nioClientHandler){
                nioClientHandler.stop();
        }
        nioClientHandler = new NioClientHandler(DEFAULT_SERVER_IP,12345);
        new Thread(nioClientHandler,"client").start();
    }

    private static boolean sendMsg(String mgs) throws IOException {
        nioClientHandler.sendMsg(mgs);
        return true;
    }

    public static void main(String[] args) throws IOException {
        start();
        Scanner scanner = new Scanner(System.in);
        while (NettyClient.sendMsg(scanner.next()));


    }

}

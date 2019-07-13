package com.yhh.netty.bio.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    private static ExecutorService executors = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket();
            serverSocket.bind(new InetSocketAddress(8099));
            System.out.println("服务端启动成功.........");
            while (true){
                executors.execute(new ThreadTask(serverSocket.accept()));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

    public static class ThreadTask implements  Runnable{

        private Socket socket;

        public ThreadTask(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            /***
             * 服务端接手请求
             */
            try{
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream()) ;
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream()) ;
                String parm = objectInputStream.readUTF();
                System.out.println("Accept client message:"+parm);
                objectOutputStream.writeUTF("Hellow  this is server response "+parm);
                objectOutputStream.flush();
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}

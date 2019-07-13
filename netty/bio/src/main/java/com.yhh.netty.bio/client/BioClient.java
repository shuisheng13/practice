package com.yhh.netty.bio.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class BioClient {


    public static void main(String[] args) throws IOException {

        Socket socket = null;
        ObjectInputStream objectInputStream = null;
        ObjectOutputStream objectOutputStream = null;
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1",9999);
        try{
            socket = new Socket();
            socket.connect(inetSocketAddress);
            objectInputStream = new ObjectInputStream(socket.getInputStream());
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeUTF("hellow  yanghh ");
            objectOutputStream.flush();
            System.out.println(objectInputStream.readUTF());
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            objectInputStream.close();
            objectOutputStream.close();
        }
    }
}

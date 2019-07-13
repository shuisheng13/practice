package com.yhh.practice.netty.server;

import io.netty.channel.ChannelHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NettyServerHandler implements  Runnable {

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private volatile  boolean start;

    public NettyServerHandler(int port) {
        try{
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port));
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            start=true;
            System.out.println("服务器已启动,  端口号 ： "+port);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
    }
    public  void stop(){
        start = false;
    }

    @Override
    public void run() {
        while (start){
            try{
                selector = Selector.open();
                Set<SelectionKey> selectionKeys =  selector.selectedKeys();
                Iterator iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = (SelectionKey) iterator.next();
                    iterator.remove();
                    try{
                        handleInput(key);
                    }catch (Exception e){
                        if(null!=key){
                        key.channel();
                        if(null!=key.channel()){
                            key.channel().close();
                        }
                        }
                    }

                }
            }catch (Exception e){
                e.printStackTrace();
            }finally {

            }
            if(null!=selector){
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    /***
     * 处理事件
     * @param selectionKey
     */
    private  void handleInput(SelectionKey selectionKey) throws IOException {
            if(selectionKey.isValid()){
                //处理新接入的请求信息
                if(selectionKey.isAcceptable()){
                    //获取关心事件的channel
                ServerSocketChannel ssc = (ServerSocketChannel) selectionKey.channel();
                //通过ServerSocketChannel的accept创建SocketChannel实例
                //完成该操作意味着完成TCP三次握手，TCP物理链路正式建立
                SocketChannel socketChannel = ssc.accept();
                System.out.println("==================== socket channel 建立连接 ");
                socketChannel.configureBlocking(false);
                //注册读事件
                socketChannel.register(selector,SelectionKey.OP_READ);
                }
                if(selectionKey.isReadable()){
                    System.out.println("======socket channel 数据准备完成，" +
                            "可以去读==读取=======");
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    int readBuffer = socketChannel.read(buffer);
                    if(readBuffer>0){
                        buffer.flip();
                        byte[] bytes = new byte[buffer.remaining()];
                        buffer.get(bytes);
                        String mes = new String(bytes,"UTF-8");
                        System.out.println("服务器收到信息 ： "+mes);
                        String res = response(mes);
                        doWrite(socketChannel,res);
                    }else if(readBuffer<0){
                        selectionKey.channel();
                        socketChannel.close();
                    }

                }
            }
        }
    public static String response(String msg){
        return "Hello,"+msg+",Now is "+new java.util.Date(
                System.currentTimeMillis()).toString() ;
    }
    public void doWrite(SocketChannel channel,String respose){
            byte[] bytes = respose.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
        try {
            channel.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

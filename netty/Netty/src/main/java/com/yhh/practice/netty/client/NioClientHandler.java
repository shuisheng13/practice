package com.yhh.practice.netty.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/***
 * netty 客户端事件处理器
 */
public class NioClientHandler  implements  Runnable{

    private String host;
    private int port ;
    private Selector selector;
    private SocketChannel socketChannel;
    private volatile  boolean flag;

    public NioClientHandler(String host, int port) {
        this.host = host;
        this.port = port;
        try{
            selector = Selector.open();
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {
        try {
            doConnect();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
            // 连接失败则退出程序
        }
        // 通道连通了  则自旋方式取获取数据
        while (flag){
            try {
                //阻塞只有当一个注册事件发生的时候才会继续
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                Iterator<SelectionKey> it =  keys.iterator();
                SelectionKey key = null;
                while (it.hasNext()){
                key = it.next();
                it.remove();
                try{
                    handInput(key);
                }catch (Exception e){
                    e.printStackTrace();
                    if(key!=null){
                        key.cancel();
                        if(key.channel()!=null){
                            key.channel().close();
                        }
                    }
                }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(selector!=null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void stop(){
        flag = false;
    }

    /***
     * 处理具体的类型事件
     * @param selectionKey
     */
    private void handInput(SelectionKey selectionKey) throws IOException {
        //如果是有效事件
        if(selectionKey.isValid()){
            //获取关注该事件的channel
            SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
            //如果是连接事件
            if(selectionKey.isConnectable()){
                if(socketChannel.finishConnect()){
                }else{
                System.exit(1);
                }
            }
            //如果是可读事件
            if(selectionKey.isReadable()){
                ByteBuffer buffer = ByteBuffer.allocate(1024);
                //将channel 的数据读到buffer 缓冲区
                int readBuffers = socketChannel.read(buffer);
                //有数据读到
                if(readBuffers>0){
                    //将缓冲区当前的limit设置postion= 0
                    //用于后续对缓冲区的读取操作
                    buffer.flip();
                    byte[]bytes =new byte[buffer.remaining()];
                    buffer.get(bytes);
                    String st = new String(bytes,"UTF-8");
                    System.out.println("accept message : "+st);
                }else if (readBuffers<0){
                    selectionKey.cancel();
                    socketChannel.close();
                }
            }

        }

    }

    private void doConnect() throws IOException {
        //判断通道是否连接 如果立即建立连接 返回 true 否则 返回  false
        if(socketChannel.connect(new InetSocketAddress(host,port))){
            // do nothing
        }else{
            // 如果通道未连接  注册 连接就绪事件向 selector表示关注这个事件
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
        }
    }
    //发送消息
    private void doWrite(SocketChannel channel ,String request) throws IOException {
            byte[] bytes =  request.getBytes();
            ByteBuffer buffer = ByteBuffer.allocate(bytes.length);
            buffer.put(bytes);
            buffer.flip();
            channel.write(buffer);
    }
    //对外暴露API 写数据
    public  void sendMsg(String msg) throws IOException {
        socketChannel.register(selector,SelectionKey.OP_READ);
        doWrite(socketChannel,msg);
    }
}


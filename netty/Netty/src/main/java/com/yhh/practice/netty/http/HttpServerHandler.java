package com.yhh.practice.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.ssl.SslContext;

/***
 *  服务端handler
 */
public class HttpServerHandler extends ChannelInitializer<SocketChannel> {
    private final SslContext sslCtx;

    public HttpServerHandler(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        ChannelPipeline channelPipeline =  socketChannel.pipeline();
        if(null!=sslCtx){
            channelPipeline.addLast(sslCtx.newHandler(socketChannel.alloc()));
        }
        channelPipeline.addLast("encode",new HttpResponseDecoder());
        channelPipeline.addLast("decode",new HttpRequestDecoder());
        channelPipeline.addLast("aggre",new HttpObjectAggregator(10*1024*1024));
        channelPipeline.addLast("compressor",new HttpContentCompressor());
        channelPipeline.addLast("busi",new BusiHandler());
    }
}

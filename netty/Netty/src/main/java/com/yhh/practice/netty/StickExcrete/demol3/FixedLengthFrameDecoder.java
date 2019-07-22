package com.yhh.practice.netty.StickExcrete.demol3;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int fiexedLength;

    public FixedLengthFrameDecoder(int fiexedLength) {
        if(fiexedLength<=0){
            throw new IllegalArgumentException(
                    "frameLength must be a positive integer: " + fiexedLength);
        }
        this.fiexedLength = fiexedLength;
    }


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        //检查是否有足够字节会被读取,以便生成下一帧
        if(byteBuf.readableBytes()>=fiexedLength){
            ByteBuf byteBuf1 =  byteBuf.readBytes(fiexedLength);
            //将该帧添加到已被解码的消息列表中
            list.add(byteBuf1);
        }
    }
}

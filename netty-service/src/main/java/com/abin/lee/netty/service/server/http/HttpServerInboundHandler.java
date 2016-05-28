package com.abin.lee.netty.service.server.http;

import com.abin.lee.netty.model.proto.MessageInfoProtoBuf;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpRequest;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class HttpServerInboundHandler extends ChannelInboundHandlerAdapter {

    private static Log log = LogFactory.getLog(HttpServerInboundHandler.class);

    private HttpRequest request;

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("server channelRead..");
        System.out.println("ctx={} msg={}"+ctx+"  msg"+msg);

        if(msg instanceof ByteBuf){
            ByteBuf buf = (ByteBuf)msg;
            if(buf.readableBytes()>=4){
                buf.markReaderIndex();
                int totalLen = buf.readInt();
                buf.resetReaderIndex();
                ByteBuf in = PooledByteBufAllocator.DEFAULT.buffer(totalLen+4);
                in.writeBytes(buf);

//                byte[] bytes = new byte[in];
                MessageInfoProtoBuf.MessageInfo messageInfo = MessageInfoProtoBuf.MessageInfo.parseFrom(in.array());

            }
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        log.error(cause.getMessage());
        ctx.close();
    }

}


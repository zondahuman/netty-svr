package com.abin.lee.netty.client.four;

import com.abin.lee.netty.model.proto.MsgHeaderProto;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.logging.Logger;

public class GateWayChannelHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(GateWayChannelHandler.class.getName());

    public GateWayChannelHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int param = 10;
        ByteBuf out = PooledByteBufAllocator.DEFAULT.directBuffer();
        MsgHeaderProto.MsgHeader msgHeader = req(param);
        byte[] msgHeaderByteArray = msgHeader.toByteArray();
        out.writeInt(msgHeaderByteArray.length + 4);
        out.writeInt(msgHeaderByteArray.length);
        out.writeBytes(msgHeaderByteArray);
        ctx.writeAndFlush(out.retain());
        out.release();
    }

    private MsgHeaderProto.MsgHeader req(int i) {
        MsgHeaderProto.MsgHeader.Builder msgHeaderBuilder = MsgHeaderProto.MsgHeader.newBuilder();
        msgHeaderBuilder.addAddress("beijing");
        msgHeaderBuilder.setBodyLength(0);
        msgHeaderBuilder.setMessageType(MsgHeaderProto.MsgHeader.MessageType.ANDROID);
        msgHeaderBuilder.setPageNum(1);
        msgHeaderBuilder.setPageSize(10);
        msgHeaderBuilder.setPassWord("abinpwd");
        msgHeaderBuilder.setQuery("param");
        msgHeaderBuilder.setUserId(i);
        msgHeaderBuilder.setUserName("abin");
        return msgHeaderBuilder.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("receive server response:[" + msg + "]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warning("unexpected exception from downstream:" + cause.getMessage());
        ctx.close();
    }

}

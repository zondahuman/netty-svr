package com.abin.lee.netty.service.server.customer.two;


import com.abin.lee.netty.common.util.JsonUtil;
import com.abin.lee.netty.model.proto.SubscribeReqProto;
import com.abin.lee.netty.model.proto.SubscribeRespProto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("msg:["+ msg);
        SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq)msg;
        ChannelMaps.put(req.getSubReqID()+"", ctx.channel());
        System.out.println("ChannelMaps="+ JsonUtil.toJson(ChannelMaps.cacheChannel.asMap()));

        //System.out.println("SubReqServerHandler channelRead:"+ req.getUserName());
        if("leeka".equalsIgnoreCase(req.getUserName())){
            System.out.println("service accept client subscribe req:["+ req +"]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }

    private SubscribeRespProto.SubscribeResp resp(int subReqID){
        SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
        builder.setSubReqID(subReqID);
        builder.setRespCode("0");
        builder.setDesc("Netty book order succeed, 3 days later, sent to the designated address");
        return builder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}

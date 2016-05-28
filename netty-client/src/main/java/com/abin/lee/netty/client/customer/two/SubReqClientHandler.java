package com.abin.lee.netty.client.customer.two;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;


import com.abin.lee.netty.model.proto.SubscribeReqProto;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class SubReqClientHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = Logger.getLogger(SubReqClientHandler.class.getName());

    public SubReqClientHandler() {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        for (int i = 0; i < 2; i++) {
            System.out.println("req(i)="+req(i));
            System.out.println("req(i).toByteArray()="+req(i).toByteArray());
            ctx.write(req(i));
        }
        ctx.flush();
    }

    private SubscribeReqProto.SubscribeReq req(int i){
        SubscribeReqProto.SubscribeReq.Builder r = SubscribeReqProto.SubscribeReq.newBuilder();
        r.setSubReqID(i);
        r.setProductName("Netty Book"+i);
        r.setUserName("leeka");

        List<String> address = new ArrayList<String>();
        address.add("Nanjing");
        address.add("Beijing");
        r.addAllAddress(address);
        return r.build();
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //super.channelReadComplete(ctx);
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception {
        System.out.println("receive server response:["+msg+"]");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {
        logger.warning("unexpected exception from downstream:"+ cause.getMessage());
        ctx.close();
    }

}

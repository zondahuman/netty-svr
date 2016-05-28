package com.abin.lee.netty.service.proto.test;


import java.util.ArrayList;
import java.util.List;

import com.abin.lee.netty.model.proto.SubscribeReqProto;
import com.google.protobuf.InvalidProtocolBufferException;

public class TestSubscribeReq {

    private static byte [] encode(SubscribeReqProto.SubscribeReq req){
        return req.toByteArray();
    }

    private static SubscribeReqProto.SubscribeReq decode(byte [] body)
            throws InvalidProtocolBufferException{
        return SubscribeReqProto.SubscribeReq.parseFrom(body);
    }

    private static SubscribeReqProto.SubscribeReq createSubscribeReq(){
        SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
        builder.setSubReqID(1);
        builder.setUserName("leeka");
        builder.setProductName("Netty book");

        List<String> address = new ArrayList<String>();
        address.add("Nanjing");
        address.add("Beijing");
        address.add("Hangzhou");
        builder.addAllAddress(address);
        return builder.build();
    }


    public static void main(String[] args)throws Exception {
        SubscribeReqProto.SubscribeReq req = createSubscribeReq();
        System.out.println("before encode:"+ req.toString());
        SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
        System.out.println("after encode:"+ req2.toString());
        System.out.println("Assert equal: " + req2.equals(req));

    }

}

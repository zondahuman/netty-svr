package com.abin.lee.netty.service.server.customer.two;

import com.abin.lee.netty.model.proto.SubscribeReqProto;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by abin
 * Be Created in 2016/5/27.
 */
public class AutoPush {
    static Timer timer;

    public AutoPush() {
        timer = new Timer();
        timer.schedule(new RemindTask(), 5000, 2000);
    }

    static class RemindTask extends TimerTask {
        public void run() {
            System.out.println("AutoPush-ChannelMaps-start");
            System.out.println("AutoPush-ChannelMaps="+ChannelMaps.cacheChannel.asMap());
            Channel channel = ChannelMaps.get("1");
            System.out.println("AutoPush-ChannelMaps-channel="+channel);
            if(null != channel){
                SubscribeReqProto.SubscribeReq.Builder r = SubscribeReqProto.SubscribeReq.newBuilder();
                r.setSubReqID(1);
                r.setProductName("auto push"+100000);
                r.setUserName("leepush");

                List<String> address = new ArrayList<String>();
                address.add("NewYork");
                address.add("NewYork");
                r.addAllAddress(address);
                channel.writeAndFlush(r);
                System.out.println("Time''s up!");
                timer.cancel();
            }

            System.out.println("AutoPush-ChannelMaps-end");
        }
    }


    public static void main(String[] args) {
        System.out.println("AutoPush initialing");
        AutoPush autoPush = new AutoPush();
        autoPush.timer.schedule(new AutoPush.RemindTask(), 1000);
        System.out.println("AutoPush ending");
    }

}

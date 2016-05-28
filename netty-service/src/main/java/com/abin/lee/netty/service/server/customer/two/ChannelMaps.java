package com.abin.lee.netty.service.server.customer.two;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import io.netty.channel.Channel;

/**
 * Created by abin
 * Be Created in 2016/5/27.
 */
public class ChannelMaps {
    public static Cache<String, Channel> cacheChannel = CacheBuilder.newBuilder().maximumSize(1000).build();

    public static void put(String key, Channel channel){
        cacheChannel.put(key, channel);
    }

    public static Channel get(String key){
        return cacheChannel.getIfPresent(key);
    }


    public static void remove(String key){
        cacheChannel.invalidate(key);
    }


}

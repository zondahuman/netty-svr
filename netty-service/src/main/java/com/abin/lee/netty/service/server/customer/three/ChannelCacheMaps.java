package com.abin.lee.netty.service.server.customer.three;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: tinkpad
 * Date: 16-5-29
 * Time: 下午2:15
 * To change this template use File | Settings | File Templates.
 */
public class ChannelCacheMaps {
    private static LoadingCache<String, String> loadingCache = CacheBuilder.newBuilder()
            .maximumSize(1000)
            .expireAfterAccess(100, TimeUnit.MINUTES)
            .build(new CacheLoader<String, String>() {
                @Override
                public String load(String s) throws Exception {
                    return defaultValue(s);
                }
            });

    public static void put(String key, String value) {
        loadingCache.put(key, value);
    }

    public static void remove(String key) {
        loadingCache.invalidate(key);
    }

    public static String get(String key) throws ExecutionException {
        return loadingCache.get(key);
    }

    public static String set(String key) throws ExecutionException {
        return loadingCache.apply(key);
    }

    public static String defaultValue(String key) {
        return "hello-"+ key;
    }

    public static void main(String[] args) throws ExecutionException {
        System.out.println("abin value:"+ loadingCache.getIfPresent("abin"));
        System.out.println("abin value:"+ loadingCache.getUnchecked("abin"));
        System.out.println("abin value:"+ loadingCache.get("abin"));
        loadingCache.put("abin", "abin1");
        System.out.println("lee value:"+ loadingCache.get("abin"));
    }
}

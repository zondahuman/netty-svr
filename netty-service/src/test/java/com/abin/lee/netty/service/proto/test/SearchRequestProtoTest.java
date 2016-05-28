package com.abin.lee.netty.service.proto.test;

import com.abin.lee.netty.common.util.JsonUtil;
import com.abin.lee.netty.model.proto.SearchRequestProto;
import com.google.common.collect.Lists;
import com.google.protobuf.InvalidProtocolBufferException;
import org.junit.Test;

import java.util.List;

/**
 * Created by tinkpad on 2016/5/8.
 */
public class SearchRequestProtoTest {

    @Test
    public void testSearchRequestProto1() throws InvalidProtocolBufferException {
        SearchRequestProto.SearchRequest.Builder search = SearchRequestProto.SearchRequest.newBuilder();
        String[] addressArray = new String[5];
        for(int i=0;i<5;i++){
            addressArray[i] = "Cool City "+i;
            search.addAddress("Cool City "+i);
        }
        search.setMessageType(SearchRequestProto.SearchRequest.MessageType.ANDROID);
        search.setPageNum(2);
        search.setPageSize(20);
        search.setPassWord("lee111");
        search.setQuery("young");
        search.setUserId(1111111111111111L);
        search.setUserName("youngabin");
        SearchRequestProto.SearchRequest searchRequest = search.build();
        System.out.println("searchRequest="+ searchRequest.toByteArray());
        System.out.println("search.build()="+ search.build());
        SearchRequestProto.SearchRequest searchResponse = SearchRequestProto.SearchRequest.parseFrom(searchRequest.toByteArray());
        System.out.println("searchResponse="+ searchResponse);

    }

    @Test
    public void testSearchRequestProto2() throws InvalidProtocolBufferException {
        SearchRequestProto.SearchRequest.Builder search = SearchRequestProto.SearchRequest.newBuilder();
        List<String> addressList= Lists.newArrayList();
        for(int i=0;i<5;i++){
            addressList.add("Cool City "+i);
        }
        search.addAllAddress(addressList);
        search.setMessageType(SearchRequestProto.SearchRequest.MessageType.ANDROID);
        search.setPageNum(2);
        search.setPageSize(20);
        search.setPassWord("lee111");
        search.setQuery("young");
        search.setUserId(1111111111111111L);
        search.setUserName("youngabin");
        SearchRequestProto.SearchRequest searchRequest = search.build();
        System.out.println("searchRequest="+ searchRequest.toByteArray());
        System.out.println("search.build()="+ search.build());
        SearchRequestProto.SearchRequest searchResponse = SearchRequestProto.SearchRequest.parseFrom(searchRequest.toByteArray());
        System.out.println("searchResponse="+ searchResponse);

    }


}

package com.zhangchun.elasticsearch.request;

import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 第一种方式，使用Builder
 */
public class RequestBuilderApp {
    public static void main(String[] args)throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        UpdateRequestBuilder updateRequestBuilder = client.prepareUpdate()
                .setDoc();
        UpdateResponse updateResponse = updateRequestBuilder.get();

        client.close();
    }
}

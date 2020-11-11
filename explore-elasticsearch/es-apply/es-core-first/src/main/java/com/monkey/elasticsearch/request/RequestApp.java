package com.monkey.elasticsearch.request;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 使用request
 */
public class RequestApp {
    public static void main(String[] args)throws Exception {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        IndexRequest indexRequest = new IndexRequest().id("1");

        client.index(indexRequest);

    }
}

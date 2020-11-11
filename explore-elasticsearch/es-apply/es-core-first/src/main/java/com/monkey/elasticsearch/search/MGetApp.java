package com.monkey.elasticsearch.search;

import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.get.MultiGetItemResponse;
import org.elasticsearch.action.get.MultiGetResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * MGet 一次性查询多个id
 */
public class MGetApp {

    public static void main(String[] args)throws Exception {

        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));
        MultiGetResponse responses = client.prepareMultiGet()
                .add("car_shop", "cars", "1")
                .add("car_shop", "cars", "2")
                .get();

        for (MultiGetItemResponse respons : responses) {
            GetResponse response = respons.getResponse();
            if (response.isExists()){
                System.out.println(response.getSourceAsString());
            }
        }
    }
}

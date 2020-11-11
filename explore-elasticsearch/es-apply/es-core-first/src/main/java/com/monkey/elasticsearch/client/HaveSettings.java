package com.monkey.elasticsearch.client;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 有设置settings
 */
public class HaveSettings {

    public static void main(String[] args)throws Exception {

        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();
        TransportClient client= new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));
    }

}

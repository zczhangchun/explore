package com.monkey.elasticsearch.upsert;

import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

/**
 * upsert用法
 * 需求：
 * 首先的话呢，第一次调整宝马320这个汽车的售价，我们希望将售价设置为32万，
 * 用一个upsert语法，如果这个汽车的信息之前不存在，那么就insert，如果存在，那么就update
 */
public class UpsertApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("elasticsearch04"), 9300));


    }
}

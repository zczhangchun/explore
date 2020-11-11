package com.monkey.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 各种搜索API
 * 1、检索查询
 * 2、多字段检索查询
 * 3、不分词查询
 * 4、前缀查询
 */
public class QueryBuilderApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        SearchResponse searchResponse;
        //1、检索查询
        searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setQuery(QueryBuilders.matchQuery("brand", "宝马"))
                .get();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println("======================================================================");

        //2、多字段检索查询
        searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setQuery(QueryBuilders.multiMatchQuery("宝马", "brand", "name"))
                .get();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println("======================================================================");

        //3、不分词查询
        searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setQuery(QueryBuilders.termQuery("name", "宝马320"))
                .get();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println("======================================================================");

        //4、前缀查询
        searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setQuery(QueryBuilders.prefixQuery("name", "宝"))
                .get();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println("======================================================================");

    }
}

package com.monkey.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * （1）搜索职位中包含technique的员工
 * （2）同时要求age在18到22岁之间
 * （3）分页查询，查找1~3条数据
 */
public class SearchApp {
    public static void main(String[] args)throws Exception {

        //设置连接参数
        Settings settings = Settings.builder().put("cluster.name","elasticsearch").build();
        //获取客户端
        TransportClient client = new PreBuiltTransportClient(settings);
        //给客户端添加es地址
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"),9300));

        //准备数据
        prepareData(client);
        //查询
        search(client);
        //客户端关闭
        client.close();
    }

    //查询
    private static void search(TransportClient client) {
        SearchResponse response = client.prepareSearch("company")
                .setTypes("employee")
                .setQuery(QueryBuilders.matchQuery("position", "technique"))
                .setPostFilter(QueryBuilders.rangeQuery("age").from(18).to(21))
                .setFrom(1).setSize(3).get();
        SearchHit[] hits = response.getHits().getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSource());
        }

    }

    //插入4条数据
    private static void prepareData(TransportClient client)throws Exception {

        client.prepareIndex("company", "employee", "2")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("name", "mike")
                        .field("age", 21)
                        .field("position", "technique")
                        .field("province", "china")
                        .field("salary", 13000)
                        .field("entry", "2020-01-01")
                        .endObject()
                ).get();

        client.prepareIndex("company", "employee", "3")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("name", "mike")
                        .field("age", 22)
                        .field("position", "technique")
                        .field("province", "usa")
                        .field("salary", 15000)
                        .field("entry", "2020-01-01")
                        .endObject()
                ).get();

        client.prepareIndex("company", "employee", "4")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("name", "mike")
                        .field("age", 21)
                        .field("position", "technique")
                        .field("province", "china")
                        .field("salary", 13000)
                        .field("entry", "2020-01-04")
                        .endObject()
                ).get();

        client.prepareIndex("company", "employee", "5")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("name", "mike")
                        .field("age", 21)
                        .field("position", "manager")
                        .field("province", "china")
                        .field("salary", 13000)
                        .field("entry", "2020-02-01")
                        .endObject()
                ).get();



    }
}

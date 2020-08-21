package com.zhangchun.elasticsearch.crud;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

public class CRUDApp {
    public static void main(String[] args) throws Exception {

        //设置连接的参数
        //不设置集群名称，默认就是elasticsearch
        Settings settings = Settings.builder().put("cluster.name", "elasticsearch").build();

        //获取客户端
        TransportClient client = new PreBuiltTransportClient(settings);

        //也可以设置空的settings
        //TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);

        //添加es地址
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));
        //增
        create(client);

        //删
        //delete(client);

        //改
        //update(client);

        //查
        //search(client);

        //客户端关闭
        client.close();

    }

    //增
    private static void create(TransportClient client) throws Exception {

        IndexResponse response = client.prepareIndex("company", "employee", "1")
                .setSource(XContentFactory.jsonBuilder().startObject()
                        .field("name", "tom")
                        .field("age", 18)
                        .field("province", "china")
                        .field("entry","2019-08-08")
                        .field("salary", 10000)
                        .endObject()
                ).get();

        System.out.println(response.getResult());

    }

    //删
    private static void delete(TransportClient client) throws Exception {
        DeleteResponse response = client.prepareDelete("company", "employee", "1").get();
        System.out.println(response.getResult());

    }

    //改
    private static void update(TransportClient client) throws Exception {
        UpdateResponse response = client.prepareUpdate("company", "employee", "2")
                .setDoc(XContentFactory.jsonBuilder().startObject()
                        .field("name", "jack")
                        .field("age", "20")
                        .endObject()
                ).get();
        System.out.println(response.getResult());
    }

    //查
    private static void search(TransportClient client)throws Exception{
        GetResponse response = client.prepareGet("company", "employee", "1").get();
        System.out.println(response.getSource());
    }
}

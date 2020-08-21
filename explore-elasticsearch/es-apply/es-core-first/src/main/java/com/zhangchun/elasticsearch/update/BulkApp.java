package com.zhangchun.elasticsearch.update;

import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 批量插入和修改操作
 * 1、插入数据
 * 2、修改数据
 * 3、删除数据
 */
public class BulkApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        //1、插入数据操作
        IndexRequest indexRequest = new IndexRequest("car_shop", "sales", "3")
                .source(XContentFactory.jsonBuilder()
                        .startObject()
                        .field("brand", "奔驰")
                        .field("name", "奔驰320")
                        .field("price", 350000)
                        .field("produce_date", "2017-01-05")
                        .field("sale_price", 340000)
                        .field("sale_date", "2017-02-03")
                        .endObject()
                );

        //2、修改数据操作
        UpdateRequest updateRequest = new UpdateRequest("car_shop", "sales", "1")
                .doc("sale_price", 290000);

        //3、删除操作
        DeleteRequest deleteRequest = new DeleteRequest("car_shop", "sales", "2");

        BulkResponse itemResponses = client.prepareBulk()
                .add(indexRequest)
                .add(updateRequest)
                .add(deleteRequest)
                .get();

        for (BulkItemResponse itemRespons : itemResponses) {
            System.out.println(itemRespons.getResponse());
        }
    }
}

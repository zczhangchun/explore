package com.zhangchun.elasticsearch.update;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 需求：
 * 如果宝马不存在，就放一个32万的宝马320进去，如果存在，就把里面的宝马320价格改成310000
 * upsert使用
 * 步骤：先创建index请求（也就是insert请求），然后把index请求放到update请求中的upsert
 */
public class UpsertApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        //先创建index
        IndexRequest indexRequest = new IndexRequest("car_shop", "cars", "1")
                .source(
                        XContentFactory.jsonBuilder()
                                .startObject()
                                .field("brand", "宝马")
                                .field("name", "宝马320")
                                .field("price", 320000)
                                .field("produce_date", "2017-01-01")
                                .endObject()
                );

        //创建update，把上面的index放到update的upsert里
        UpdateRequest updateRequest = new UpdateRequest("car_shop", "cars", "1")
                .doc(
                        XContentFactory.jsonBuilder()
                                .startObject()
                        .field("price", 310000)
                        .endObject()
                )
                .upsert(indexRequest);
        UpdateResponse updateResponse = client.update(updateRequest).get();
        System.out.println(updateResponse.getResult());


    }
}

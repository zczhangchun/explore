package com.zhangchun.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * Scroll分次读取数据
 *
 * TimeValue：一次scroll的最大时长
 *
 * 1、先使用搜索查询，搜索查询里使用scroll去查询
 * 2、使用scroll查询，scrollId是上一次scroll查询的结果
 * 3、直到没有数据了，才结束循环。
 */
public class ScrollApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        //1、先使用搜索查询，搜索查询里使用scroll去查询
        SearchResponse searchResponse = client.prepareSearch("car_shop")
                .setTypes("sales")
                .setScroll(new TimeValue(60000))
                .setQuery(QueryBuilders.termQuery("brand.keyword", "宝马"))
                .setSize(1)
                .get();
        do{
            for (SearchHit hit : searchResponse.getHits().getHits()) {
                System.out.println(hit.getSource());
            }
            //2、使用scroll查询，scrollId是上一次scroll查询的结果
            searchResponse = client.prepareSearchScroll(searchResponse.getScrollId())
                    .setScroll(new TimeValue(60000))
                    .execute()
                    .get();
            //3、直到没有数据了，才结束循环。
        }while (searchResponse.getHits().getHits().length != 0);

        client.close();
    }
}

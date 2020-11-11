package com.monkey.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * bool多条件查询
 *
 */
public class BoolQueryApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery()
                .must(QueryBuilders.matchQuery("brand", "宝马"))
                .mustNot(QueryBuilders.termQuery("name.raw", "宝马318"))
                .should(QueryBuilders.termQuery("produce_date", "2017-01-02"))
                .filter(QueryBuilders.rangeQuery("price").gte(280000).lt(350000));

        SearchResponse searchResponse = client.prepareSearch("car_shop")
                .setTypes("cars")
                .setQuery(boolQueryBuilder)
                .get();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
    }
}

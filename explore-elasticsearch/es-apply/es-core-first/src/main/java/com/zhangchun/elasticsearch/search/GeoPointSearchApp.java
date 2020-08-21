package com.zhangchun.elasticsearch.search;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.unit.DistanceUnit;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;

/**
 * 基于地理位置的查询
 * 需要导入依赖
 * <dependency>
 *     <groupId>org.locationtech.spatial4j</groupId>
 *     <artifactId>spatial4j</artifactId>
 *     <version>0.6</version>
 * </dependency>
 *
 * <dependency>
 *     <groupId>com.vividsolutions</groupId>
 *     <artifactId>jts</artifactId>
 *     <version>1.13</version>
 *     <exclusions>
 *         <exclusion>
 *             <groupId>xerces</groupId>
 *             <artifactId>xercesImpl</artifactId>
 *         </exclusion>
 *     </exclusions>
 * </dependency>
 */
public class GeoPointSearchApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));
        SearchResponse searchResponse;
        //1、搜索两点矩阵之间的数据
        searchResponse = client.prepareSearch("car_shop")
                .setQuery(QueryBuilders.geoBoundingBoxQuery("pin.location").setCorners(40.73, -74.1, 40.01, -71.12))
                .get();
        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println("================================================");
        //2、搜索某点的200公里以内的数据
        searchResponse = client.prepareSearch("car_shop")
                .setTypes("shops")
                .setQuery(QueryBuilders.geoDistanceQuery("pin.location").point(40, -70).distance(200, DistanceUnit.KILOMETERS))
                .get();

        for (SearchHit hit : searchResponse.getHits().getHits()) {
            System.out.println(hit.getSource());
        }

    }
}

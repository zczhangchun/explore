package com.zhangchun.elasticsearch.agg;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.LongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.avg.Avg;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.List;
import java.util.Map;

/**
 * 聚合使用
 * （1）首先按照province省份来进行分组
 * （2）然后在每个province分组内，再按照入职年限进行分组
 * （3）最后计算每个分组内的平均薪资
 */
public class AggApp {
    public static void main(String[] args)throws Exception {
        //获取客户端
        //不设置集群名称，默认就是elasticsearch
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY);
        //添加es地址
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        //准备数据
        prepareData(client);

        //查询
        agg(client);
        client.close();
    }

    private static void agg(TransportClient client) {

        SearchResponse response = client.prepareSearch("company")
                .setTypes("employee")
                .addAggregation(
                        AggregationBuilders.terms("group_by_province").field("province")
                                .subAggregation(
                                        AggregationBuilders.terms("group_by_entry").field("entry")
                                                .subAggregation(
                                                        AggregationBuilders.avg("avg_by_salary").field("salary")
                                                )
                                )
                ).get();

        Map<String, Aggregation> aggregationMap = response.getAggregations().asMap();

        StringTerms groupByProvince = (StringTerms) aggregationMap.get("group_by_province");
        List<Terms.Bucket> provinceBuckets = groupByProvince.getBuckets();
        for (Terms.Bucket bucket : provinceBuckets) {
            //数据中有usa和china，所以会有两个bucket
            System.out.println(bucket.getKey() + ":" + bucket.getDocCount());
            LongTerms groupByEntry = (LongTerms) bucket.getAggregations().asMap().get("group_by_entry");
            List<Terms.Bucket> entryBuckets = groupByEntry.getBuckets();
            for (Terms.Bucket entryBucket : entryBuckets) {
                System.out.println(entryBucket.getKey() + ":" + entryBucket.getDocCount());
                Avg avgBySalary = (Avg) entryBucket.getAggregations().asMap().get("avg_by_salary");
                System.out.println("平均工资：" + avgBySalary.getValue());
            }
        }

    }

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

package com.monkey.elasticsearch;

import lombok.SneakyThrows;
import org.apache.http.HttpHost;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ImportData {

    private static List<String> vidList = new ArrayList<String>(1500000);


    public static void main(String[] args) throws Exception {
        System.out.println(System.currentTimeMillis() - 1000 * 60 * 60 * 24 * 15);

        //getVidList();
        //getEventList();
        //System.out.println("gg");

        //RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("172.16.38.201", 9200, "http"),
        //        new HttpHost("172.16.38.202", 9200, "http"),
        //        new HttpHost("172.16.38.202", 9200, "http")));
        //
        //
        //IndexRequest request = new IndexRequest("test_index")
        //        .source(XContentFactory.jsonBuilder().startObject()
        //                .field("name", "tom")
        //                .field("age", 18)
        //                .field("province", "china")
        //                .field("entry", "2019-08-08")
        //                .field("salary", 10000)
        //                .endObject());
        //new BulkRequest().add()
        //client.bulk()
        //
        //IndexResponse response = client.index(request, RequestOptions.DEFAULT);
        //
        //client.close();


    }

    public static void getVidList() {
        File file = new File("/Users/zhangchun/Downloads/part-00000的副本");
        BufferedReader reader = null;
        StringBuilder sbf = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                sbf.append(tempStr);
            }
            reader.close();
            vidList.addAll(Arrays.asList(sbf.toString().split(",")));
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    @SneakyThrows
    public static void getEventList() {
        int index = 0;
        RestHighLevelClient client = new RestHighLevelClient(RestClient.builder(new HttpHost("172.16.38.201", 9200, "http"),
                new HttpHost("172.16.38.202", 9200, "http"),
                new HttpHost("172.16.38.202", 9200, "http")));
        File file = new File("/Users/zhangchun/Downloads/value.txt");
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String tempStr;
            while ((tempStr = reader.readLine()) != null) {
                if (index == 500000) {
                    client.close();
                    return;
                }
                index++;

                if (tempStr.equals("null")) {
                    continue;
                }

                BulkRequest bulkRequest = new BulkRequest();
                for (String feature : tempStr.split(",")) {
                    String[] split = feature.split("#");
                    bulkRequest.add(new IndexRequest("event")
                            .source(XContentFactory.jsonBuilder().startObject()
                                    .field("vid", vidList.get(index))
                                    .field("game_id", split[0])
                                    .field("receive_timestamp", Long.valueOf(split[1]))
                                    .field("game_exposure_time", Integer.valueOf(split[2]))
                                    .field("$app_version", split[3])
                                    .endObject()));
                }
                client.bulk(bulkRequest, RequestOptions.DEFAULT);
            }
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }
}

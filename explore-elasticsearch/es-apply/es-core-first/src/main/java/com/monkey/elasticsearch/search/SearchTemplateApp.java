package com.monkey.elasticsearch.search;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.script.ScriptType;
import org.elasticsearch.script.mustache.SearchTemplateRequestBuilder;
import org.elasticsearch.script.mustache.SearchTemplateResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * 使用搜索模版搜索
 *
 * 已经将下面这个代码放到了config/scripts的page_query_by_brand.mustache文件下
 * {
 *   "from": {{from}},
 *   "size": {{size}},
 *   "query": {
 *     "match": {
 *       "brand.keyword": "{{brand}}"
 *     }
 *   }
 * }
 */
public class SearchTemplateApp {
    public static void main(String[] args) throws Exception {
        TransportClient client = new PreBuiltTransportClient(Settings.EMPTY).addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("common"), 9300));

        Map<String, Object> params = new HashMap<String, Object>();
        params.put("from", 0);
        params.put("size", 1);
        params.put("brand", "宝马");
        SearchTemplateResponse searchTemplateResponse = new SearchTemplateRequestBuilder(client)
                .setScript("page_query_by_brand")
                .setScriptType(ScriptType.FILE)
                .setScriptParams(params)
                .setRequest(new SearchRequest("car_shop").types("sales"))
                .get();

        for (SearchHit hit : searchTemplateResponse.getResponse().getHits().getHits()) {
            System.out.println(hit.getSource());
        }
        System.out.println(searchTemplateResponse.getSource());
    }
}

package com.monkey.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.HttpGetWithBody;
import com.monkey.QueryParam;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;

public class HttpTool {

    private static Logger logger = LoggerFactory.getLogger(HttpTool.class);

    private static String ENCODING = "UTF-8";

    private static String CONTENT_TYPE = "application/json";
    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 发送POST请求，参数是JSON
     */

    public static String requestPost(String url) throws JsonProcessingException {
        //创建HttpClient对象
        CloseableHttpClient client = HttpClients.createDefault();
        //创建HttpPost对象
        HttpGetWithBody httpGet = new HttpGetWithBody(url);

        //配置请求参数

        String respContent = null;

        //设置参数和请求方式
        StringEntity entity = new StringEntity(objectMapper.writeValueAsString(Collections.singletonList(QueryParam.builder().vid("1768gmKMqgbsb9BEo0TXj4cfb").featureId(1).build())), ENCODING);//解决中文乱码问题

        httpGet.setEntity(entity);

        HttpResponse resp;
        try {
            //执行请求
            resp = client.execute(httpGet);
            System.out.println(String.valueOf(resp.getStatusLine().getStatusCode()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        //返回数据
        return respContent;
    }

}

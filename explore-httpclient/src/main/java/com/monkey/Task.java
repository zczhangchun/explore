package com.monkey;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.util.Collections;

public class Task implements Runnable {
    @SneakyThrows
    public void run(){
        //通过URIBuilder对象创建URI对象传递给HttpGet
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(HttpApplication.httpPool).build();
        ObjectMapper objectMapper = new ObjectMapper();



        while (true){

            HttpGetWithBody httpGet = new HttpGetWithBody("http://gateway.4399fat.com/feature-server/feature/getFeatures");
            long start = System.currentTimeMillis();
            QueryParam queryParam = QueryParam.builder()
                    .vid("1099goDNloiE06iHSCOwdae9d")
                    .featureId(1)
                    .build();
            String body = objectMapper.writeValueAsString(Collections.singletonList(queryParam));
            httpGet.setEntity(new StringEntity(body, ContentType.create("application/json", "utf-8")));


            //获取数据
            CloseableHttpResponse execute = httpClient.execute(httpGet);
            //System.out.println(execute.getStatusLine().getStatusCode());
            long time = 1000 - (System.currentTimeMillis() - start);
            //System.out.println(time);
            //if (time > 0){
            //    Thread.sleep(time);
            //}

        }
    }
}

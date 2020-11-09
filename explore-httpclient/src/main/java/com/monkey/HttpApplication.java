package com.monkey;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

public class HttpApplication {

    public static PoolingHttpClientConnectionManager httpPool = new PoolingHttpClientConnectionManager();


    static {

        //设置池的最大连接数
        httpPool.setMaxTotal(2500);

        //设置访问的主机的最大连接数
        //比如同时访问百度和淘宝，访问百度的最大连接数只能有10个
        httpPool.setDefaultMaxPerRoute(2500);
    }


    public static void main(String[] args) throws Exception {
        int index = 0;
        for (int i = 0; i < 100; i++) {
            for (int j = 0; j < 25; j++) {
                System.out.println(index++);
                new Thread(new Task()).start();
            }
            Thread.sleep(1000);
        }



    }
}

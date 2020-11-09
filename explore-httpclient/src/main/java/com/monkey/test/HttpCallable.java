package com.monkey.test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class HttpCallable implements Callable<String> {

    private String url;


    public HttpCallable(String url){
        this.url = url;
    }



    /**
     * 执行并返回结果
     */
    public String call() throws Exception {
        return HttpTool.requestPost(url);
    }

    /**
     * 模拟并发测试
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        //模拟并发数
        int concurrencyNumber = 2500;

        List<String> resList = new ArrayList<String>();

        //执行线程池
        ExecutorService ex = Executors.newFixedThreadPool(1000);

        String url = "http://gateway.4399fat.com/feature-server/feature/getFeatures";
        Map<String, String> mapPatam = new HashMap<String, String>();
        mapPatam.put("name", "测试");


        for(int i =0 ; i< concurrencyNumber; i++){
            Future<String> callRes = ex.submit(new HttpCallable(url));
            resList.add(i + ">>>" + callRes.get());
        }

        for(String s : resList){
            System.out.println("------>" + s);
        }
    }


}

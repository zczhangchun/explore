package com.monkey.explore.hystrix.hystrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.explore.hystrix.http.HttpUtils;
import com.monkey.explore.hystrix.model.ProductInfo;
import com.netflix.hystrix.*;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;

/**
 * 是用来获取一条数据的
 */
public class CallInterfaceCommand extends HystrixCommand<ProductInfo> {
    private static HystrixCommandKey KEY = HystrixCommandKey.Factory.asKey("CallInterfaceCommand");
    private final Long productId;

    public CallInterfaceCommand(Long productId) {
        //线程池的组名称
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("CallInterfaceService"))
                .andCommandKey(KEY)
                .andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey("CallInterfacePool"))
                .andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter()
                        .withCoreSize(15)
                        .withQueueSizeRejectionThreshold(10))
        );
        this.productId = productId;
    }

    @Override
    protected ProductInfo run() throws Exception {
        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
        //这里我们调用了http接口，返回了数据
        String response = HttpUtils.sendGetRequest(productId);
        return new ObjectMapper().readValue(response, ProductInfo.class);
    }

    @Override
    protected String getCacheKey() {
        return "productId_" + productId;
    }

    public static void flushCache(Long productId){
        HystrixRequestCache.getInstance(KEY, HystrixConcurrencyStrategyDefault.getInstance()).clear("productId_" + productId);
    }
}

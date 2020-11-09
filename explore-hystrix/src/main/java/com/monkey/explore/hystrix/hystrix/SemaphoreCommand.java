package com.monkey.explore.hystrix.hystrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.explore.hystrix.http.HttpUtils;
import com.monkey.explore.hystrix.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandProperties;

/**
 * 信号量隔离
 */
public class SemaphoreCommand extends HystrixCommand<ProductInfo> {

    private final Long productId;

    public SemaphoreCommand(Long productId) {
        super(Setter.withGroupKey(HystrixCommandGroupKey.Factory.asKey("semaphoreGroup"))
                .andCommandPropertiesDefaults(HystrixCommandProperties.Setter()
                        .withExecutionIsolationStrategy(HystrixCommandProperties.ExecutionIsolationStrategy.SEMAPHORE)
                )
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
}

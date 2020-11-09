package com.monkey.explore.hystrix.hystrix;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.monkey.explore.hystrix.http.HttpUtils;
import com.monkey.explore.hystrix.model.ProductInfo;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import rx.Observable;
import rx.Subscriber;

import java.util.List;

/**
 * 多返回值的command
 */
public class CallInterfaceObservableCommand extends HystrixObservableCommand<ProductInfo> {


    private List<Long> productIds;

    public CallInterfaceObservableCommand(List<Long> productIds) {
        super(HystrixCommandGroupKey.Factory.asKey("CallInterfaceCommand"));
        this.productIds = productIds;
    }

    @Override
    protected Observable<ProductInfo> construct() {
        return Observable.create(new Observable.OnSubscribe<ProductInfo>() {
            @Override
            public void call(Subscriber<? super ProductInfo> subscriber) {
                try {
                    for (Long productId : productIds) {
                        String url = "http://127.0.0.1:8082/getProductInfo?productId=" + productId;
                        String response = HttpUtils.sendGetRequest(productId);
                        ProductInfo productInfo = new ObjectMapper().readValue(response, ProductInfo.class);
                        //将结果放到subscriber里
                        subscriber.onNext(productInfo);
                    }
                    //全部执行完就完成
                    subscriber.onCompleted();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }
}

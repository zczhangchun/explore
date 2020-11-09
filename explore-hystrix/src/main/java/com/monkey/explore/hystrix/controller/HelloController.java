package com.monkey.explore.hystrix.controller;

import com.monkey.explore.hystrix.hystrix.CallInterfaceCommand;
import com.monkey.explore.hystrix.hystrix.CallInterfaceObservableCommand;
import com.monkey.explore.hystrix.hystrix.SemaphoreCommand;
import com.monkey.explore.hystrix.model.ProductInfo;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.bind.annotation.RestController;
import rx.Observable;
import rx.Observer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 现在的业务背景就是要调用一个服务的接口。需要对这个调用进行资源隔离
 */
@RestController
public class HelloController {


    /**
     * 第一种，
     * 同步执行，返回单条数据的线程池隔离
     */
    public void syncSimpleResultIsolation(){
        //先创建command
        HystrixCommand<ProductInfo> hystrixCommand = new CallInterfaceCommand(1L);
        //然后执行
        ProductInfo productInfo = hystrixCommand.execute();

    }

    /**
     * 第二种
     * 异步执行，返回单条数据的线程池隔离
     */
    public void asyncSimpleResultIsolation(){
        //先创建command
        HystrixCommand<ProductInfo> hystrixCommand = new CallInterfaceCommand(1L);
        //然后获得Future对象，这个对象就是JUC里的异步对象
        Future<ProductInfo> future = hystrixCommand.queue();

        //通过这个对象获取结果
        try {
            ProductInfo productInfo = future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 第三种
     * 同步执行，返回多条数据的线程池隔离
     */
    public void syncMoreResultIsolation(){
        HystrixObservableCommand<ProductInfo> hystrixObservableCommand = new CallInterfaceObservableCommand(Arrays.asList(1L,2L));

        Observable<ProductInfo> observe = hystrixObservableCommand.observe();

        //调用这个方法后，会懒加载，即等到subscribe调用后，才会真的去执行逻辑
        //hystrixObservableCommand.toObservable()

        List<ProductInfo> productInfoList = new LinkedList<>();
        //获取数据，添加到集合中
        observe.subscribe(new Observer<ProductInfo>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(ProductInfo productInfo) {
                productInfoList.add(productInfo);
            }
        });
    }

    /**
     * 第四种
     * 异步执行，返回多条数据的线程池隔离
     */
    public void asyncMoreResultIsolation(){
        HystrixObservableCommand<ProductInfo> hystrixObservableCommand = new CallInterfaceObservableCommand(Arrays.asList(1L,2L));

        //然后获得Future对象，这个对象就是JUC里的异步对象
        Future<ProductInfo> future = hystrixObservableCommand.toObservable().toBlocking().toFuture();

        try {
            future.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 信号量隔离
     */
    public void semaphoreIsolation(){
        //先创建command
        HystrixCommand<ProductInfo> hystrixCommand = new SemaphoreCommand(1L);
        //然后执行
        ProductInfo productInfo = hystrixCommand.execute();
    }
}

package com.zhangchun.spring.interfacee.applicationListener;

import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executors;

/**
 * @author zhangchun
 */
@Component(value = "applicationEventMulticaster")
public class MyMulticaster extends SimpleApplicationEventMulticaster {
    public MyMulticaster() {
        setTaskExecutor(Executors.newSingleThreadExecutor());
    }
}

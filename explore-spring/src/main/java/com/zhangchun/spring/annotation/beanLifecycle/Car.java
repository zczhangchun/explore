package com.zhangchun.spring.annotation.beanLifecycle;

import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author zhangchun
 */
@Component
public class Car {

    @PostConstruct
    public void init(){
        System.out.println("PostConstruct");
    }

    @PreDestroy
    public void destory(){
        System.out.println("PreDestroy");
    }
}

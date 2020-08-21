package com.zhangchun.spring.annotation.beanLifecycle;

import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangchun
 */
@ComponentScan("com.zhangchun.spring.annotation.beanLifecycle")
public class MainConfig {

//    @Scope(value = "prototype")
//    @Bean(initMethod = "init", destroyMethod = "destory")
//    public Book book(){
//        return new Book();
//    }
}

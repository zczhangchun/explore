package com.zhangchun.spring.source.createBeanInst;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smlz on 2019/6/3.
 */
@Configuration
@ComponentScan(basePackages = {"com.zhangchun.spring.source.createBeanInst"})
public class MainConfig {

    @Bean(autowire = Autowire.BY_TYPE)
    public MyAspect myAspect() {
        return new MyAspect();
    }

    @Bean
//    @Primary
    public MyLog myLog() {
        return new MyLog();
    }

//    @Bean
//    public MyLog myLog2() {
//        return new MyLog();
//    }

}

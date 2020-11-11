package com.monkey.spring.annotation.conditional.config;

import com.monkey.spring.annotation.conditional.condition.MyCondition;
import com.monkey.spring.annotation.conditional.entity.MyAspect;
import com.monkey.spring.annotation.conditional.entity.MyLog;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Conditional;

/**
 * @author zhangchun
 */
@Configurable
@ComponentScan(basePackages = {"com.monkey.spring.annotation.conditional"})
public class MainConfig {
    @Bean
    public MyAspect myAspect(){
        return new MyAspect();
    }
    @Bean
    //条件是：有MyAspect这个bean，MyLog才能被注入。
    @Conditional(MyCondition.class)
    public MyLog myLog(){
        return new MyLog();
    }
}

package com.monkey.spring.annotation.scope.config;

import com.monkey.spring.annotation.scope.entity.Person;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author zhangchun
 */
@Configurable
//基本使用
@ComponentScan(basePackages = {"com.monkey.spring.annotation.scope"})
public class MainConfig {

//    @Bean
    //不写scope注解，和写了注解不写参数，一样都是单例饿汉
//    @Scope
//    @Lazy
//    @Scope(value = "prototype")
    public Person person(){
        return new Person();
    }
}

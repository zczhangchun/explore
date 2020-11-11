package com.monkey.spring.interfacee.iocStart;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by smlz on 2019/6/12.
 */
@Configuration
//@Import(value = {MyService.class})
@ComponentScan(basePackages = "com.monkey.spring.interfacee.iocStart")
public class MainConfig {

    @Bean
    public MyDuplicate myDuplicate(){
        return new MyDuplicate();
    }
//    @Bean
//    public MyDao myDao(MyService myService){
//        return new MyDao(myService);
//    }

//    @Bean(autowire = )
//    public MyService myService(){
//        return new MyService();
//    }

    
}

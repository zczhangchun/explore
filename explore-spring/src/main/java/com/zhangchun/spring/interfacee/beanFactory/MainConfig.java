package com.zhangchun.spring.interfacee.beanFactory;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author zhangchun
 */
@Configuration
public class MainConfig {

    @Bean
    public CarFactoryBean carFactoryBean(){
        return new CarFactoryBean();
    }

}

package com.zhangchun.spring.annotation.dependsOn;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

/**
 * @author zhangchun
 */
@Configuration
public class MainConfig {

    @Bean
    public DependsA dependsA(){
        return new DependsA();
    }

    @Bean
    @DependsOn(value = {"depenedsA"})
    public DependsB dependsB(){
        return new DependsB();
    }
}

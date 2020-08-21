package com.zhangchun.spring.source.aop;

import org.springframework.context.annotation.*;

/**
 * Created by smlz on 2019/6/10.
 */
@Configuration
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class MainConfig {

    @Bean
    public Calculate calculate() {
        return new MyCalculate();
    }

    @Bean
    public MyLogAspect myLogAspect() {
        return new MyLogAspect();
    }
}

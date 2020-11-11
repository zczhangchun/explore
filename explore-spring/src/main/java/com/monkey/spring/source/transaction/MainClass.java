package com.monkey.spring.source.transaction;

import com.monkey.spring.source.transaction.config.MainConfig;
import com.monkey.spring.source.transaction.service.PayService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by smlz on 2019/6/17.
 */
public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        PayService payService = context.getBean(PayService.class);
        payService.pay("123456789",10);
        payService.updateProductStore(111);
    }
}

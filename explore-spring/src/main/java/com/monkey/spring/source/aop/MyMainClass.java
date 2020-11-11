package com.monkey.spring.source.aop;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by smlz on 2019/6/10.
 */
public class MyMainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        Calculate calculate = (Calculate) ctx.getBean("calculate");
        int retVal = calculate.mod(2,4);
        System.out.println("运算结果是:"+retVal);
    }
}

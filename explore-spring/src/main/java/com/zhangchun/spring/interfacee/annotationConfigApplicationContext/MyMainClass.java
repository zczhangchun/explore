package com.zhangchun.spring.interfacee.annotationConfigApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MyMainClass {
    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext ctx = new MyAnnotationConfigApplicationContext(MainConfig.class);
    }
}

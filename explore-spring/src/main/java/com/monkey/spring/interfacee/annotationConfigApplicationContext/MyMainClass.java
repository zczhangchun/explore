package com.monkey.spring.interfacee.annotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MyMainClass {
    public static void main(String[] args) {
        MyAnnotationConfigApplicationContext ctx = new MyAnnotationConfigApplicationContext(MainConfig.class);
    }
}

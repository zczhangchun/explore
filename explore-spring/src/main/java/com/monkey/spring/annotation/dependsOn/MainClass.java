package com.monkey.spring.annotation.dependsOn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
    }
}

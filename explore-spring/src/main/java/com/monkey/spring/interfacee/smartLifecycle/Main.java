package com.monkey.spring.interfacee.smartLifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(MainConfig.class);

        ac.start();
//        ac.stop();
    }
}

package com.monkey.spring.source.createBeanInst;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by smlz on 2019/6/3.
 */
public class MainClass {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainConfig.class);
        MyAspect bean = context.getBean(MyAspect.class);
        System.out.println(bean);
        System.out.println(bean.getMyLog());
    }
}

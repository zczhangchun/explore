package com.monkey.spring.interfacee.iocStart;

import org.springframework.beans.BeansException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args) throws BeansException {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
//        System.out.println(ctx.getBean(MyDao.class));
        MyDuplicate bean = ctx.getBean(MyDuplicate.class);
        System.out.println(bean);
        bean.run();


    }
}

package com.zhangchun.spring.annotation.beanLifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args)throws Exception {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        //不执行close，则无法执行销毁
//        Book bean = ctx.getBean(Book.class);
//        Person bean = ctx.getBean(Person.class);
        Car bean = ctx.getBean(Car.class);
        ctx.close();
    }
}

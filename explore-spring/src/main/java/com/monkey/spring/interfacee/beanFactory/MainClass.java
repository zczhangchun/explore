package com.monkey.spring.interfacee.beanFactory;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        //通过工厂返回的不是工厂本身，而是工厂的getObject的对象
        Object carFactoryBean1 = ctx.getBean("carFactoryBean");
        //加了'&'后，返回的就是工厂本身的对象
        Object carFactoryBean = ctx.getBean("&carFactoryBean");
        System.out.println(carFactoryBean);
    }
}

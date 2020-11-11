package com.monkey.spring.annotation.scope;

import com.monkey.spring.annotation.scope.config.MainConfig;
import com.monkey.spring.annotation.scope.entity.Person;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        Person per = ctx.getBean(Person.class);
        Person per2 = ctx.getBean(Person.class);
        System.out.println(per == per2);
    }
}

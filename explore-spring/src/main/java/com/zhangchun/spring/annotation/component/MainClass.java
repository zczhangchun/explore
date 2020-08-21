package com.zhangchun.spring.annotation.component;

import com.zhangchun.spring.annotation.component.config.MainConfig;
import com.zhangchun.spring.annotation.component.dao.MyDao;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */

public class MainClass {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] beanDefinitionNames = ctx.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println("bean定义的信息：" + beanDefinitionName);
        }
        System.out.println(ctx.getBean(MyDao.class));
    }
}

package com.monkey.spring.annotation.beanLifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class Person implements InitializingBean, DisposableBean {
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean的afterPropertiesSet");
    }

    //单例destroy是会等容器关闭时执行，多例等GC
    public void destroy() throws Exception {
        System.out.println("DisposableBean的destroy");
    }
}

package com.zhangchun.spring.interfacee.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zhangchun
 */
public class MyComponent implements ApplicationContextAware {
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }
}

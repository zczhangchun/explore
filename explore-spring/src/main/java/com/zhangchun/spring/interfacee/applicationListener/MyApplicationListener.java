package com.zhangchun.spring.interfacee.applicationListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class MyApplicationListener implements ApplicationListener {
    public void onApplicationEvent(ApplicationEvent event) {
        System.out.println("监听到事件：" + event);
    }
}

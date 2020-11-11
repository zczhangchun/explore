package com.monkey.spring.interfacee.applicationListener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MainClass {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        ctx.publishEvent(new ApplicationEvent("手动发布事件") {
            @Override
            public Object getSource() {
                return super.getSource();
            }
        });

        ctx.close();
    }
}

package com.zhangchun.spring.interfacee.annotationConfigApplicationContext;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author zhangchun
 */
public class MyAnnotationConfigApplicationContext extends AnnotationConfigApplicationContext {
    @Override
    protected void initPropertySources() {
        getEnvironment().setRequiredProperties("my");
    }
    public MyAnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
        super(annotatedClasses);
    }
}

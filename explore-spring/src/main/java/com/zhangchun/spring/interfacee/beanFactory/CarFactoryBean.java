package com.zhangchun.spring.interfacee.beanFactory;

import com.zhangchun.spring.annotation.importt.entity.Car;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class CarFactoryBean implements FactoryBean {
    public Object getObject() throws Exception {
        return new Car();
    }
    public Class<?> getObjectType() {
        return Car.class;
    }
    public boolean isSingleton() {
        return false;
    }
}

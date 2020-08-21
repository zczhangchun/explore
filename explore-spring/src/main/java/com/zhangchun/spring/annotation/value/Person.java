package com.zhangchun.spring.annotation.value;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class Person {
    @Value("zc")
    private String name;
    @Value("#{29 - 8}")
    private Integer age;
    @Value("${person.lastName}")
    private String lastName;
    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", lastName='" + lastName + '\'' +
                '}';
    }
}

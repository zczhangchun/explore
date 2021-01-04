package com.monkey.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangchun
 */
@SpringBootApplication
public class ElementApplication {
    public static void main(String[] args) {
//        new SpringApplication(ElementApplication.class).run();
        SpringApplication.run(ElementApplication.class, args);
    }
}

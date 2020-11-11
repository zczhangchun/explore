package com.monkey.security.practice;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.monkey.security.practice.mapper")
public class PracticeApplication {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(PracticeApplication.class);
    }
}

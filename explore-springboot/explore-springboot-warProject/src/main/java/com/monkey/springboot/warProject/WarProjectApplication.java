package com.monkey.springboot.warProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

/**
 * @author zhangchun
 */
@SpringBootApplication
public class WarProjectApplication extends SpringBootServletInitializer {
    public static void main(String[] args) {
        SpringApplication.run(WarProjectApplication.class, args);
    }
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(WarProjectApplication.class);
    }
}

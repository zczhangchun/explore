package com.zhangchun.springboot.webjarProject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by smlz on 2019/5/21.
 */
@Configuration
public class MyWebMvcConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("index.html");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] add = {"/**"};
        String[] exclude = {};
//        registry.addInterceptor(new MyInterceptor())
//                .addPathPatterns(add)
//        .excludePathPatterns(exclude);
    }
}

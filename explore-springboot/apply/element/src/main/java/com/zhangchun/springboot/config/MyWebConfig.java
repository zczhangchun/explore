package com.zhangchun.springboot.config;

import com.zhangchun.springboot.filter.MyFilter;
import com.zhangchun.springboot.interceptors.MyInterceptor;
import com.zhangchun.springboot.servlet.MyServlet;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zhangchun
 */
@Configuration
public class MyWebConfig implements WebMvcConfigurer {
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("index.html");
    }
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        String[] add = {"/**"};
        String[] exclude = {};
        registry.addInterceptor(new MyInterceptor())
                .addPathPatterns(add)
                .excludePathPatterns(exclude);
    }
    @Bean
    public ServletRegistrationBean myServlet() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new MyServlet(), "/my");
        return servletRegistrationBean;
    }

    @Bean
    public FilterRegistrationBean myFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new MyFilter());
        return filterRegistrationBean;
    }
}

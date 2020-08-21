package com.zhangchun.spring.annotation.value;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author zhangchun
 */
@Configuration
@ComponentScan("com.zhangchun.spring.annotation.value")
@PropertySource({"classpath:person.properties"})
public class MainConfig {
}

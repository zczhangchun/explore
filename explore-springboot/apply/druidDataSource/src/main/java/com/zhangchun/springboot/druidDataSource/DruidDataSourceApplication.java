package com.zhangchun.springboot.druidDataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangchun
 */
@SpringBootApplication
public class DruidDataSourceApplication {
    //使用-Ddruid.filters=mergeStat -Ddruid.useGlobalDataSourceStat=true
    //才能够看到sql监控
    public static void main(String[] args) {
        SpringApplication.run(DruidDataSourceApplication.class, args);
    }
}

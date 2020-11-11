package com.monkey.mybatis.multiDataSource;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author zhangchun
 */
@SpringBootApplication
@MapperScan("com.monkey.mybatis.multiDataSource")
public class multiDataSourceApplication {
    public static void main(String[] args) {

    }
}

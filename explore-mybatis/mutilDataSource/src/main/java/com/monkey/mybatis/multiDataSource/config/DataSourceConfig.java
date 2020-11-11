package com.monkey.mybatis.multiDataSource.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author zhangchun
 */
@Configuration
public class DataSourceConfig {

    @Bean
    public DataSource hadoopDatSource() {

    }
}

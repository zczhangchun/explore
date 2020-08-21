package com.monkey.mybatis.config.mybatis;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;


@Configuration
@EnableCaching
@Slf4j
// 扫描 Mapper 接口并容器管理
@MapperScan(basePackages = promotionMyBatisConfig.PACKAGE, sqlSessionFactoryRef = "promotionSqlSessionFactory")
public class promotionMyBatisConfig {


    // 精确到 promotion 目录，以便跟其他数据源隔离
    static final String PACKAGE = "com.monkey.mybatis.mapper";
    static final String MAPPER_LOCATION = "classpath:mapper/*.xml";


    @Bean(name = "promotionDataSource")
    @ConfigurationProperties(prefix = "promotion.tuijian.mysql")
    public DataSource promotionDataSource() {
        return DataSourceBuilder.create().build();
    }



    @Bean(name = "promotionTransactionManager")
    @Primary
    public DataSourceTransactionManager promotionTransactionManager(@Qualifier("promotionDataSource") DataSource promotionDataSource) {
        return new DataSourceTransactionManager(promotionDataSource);
    }

    @Bean(name = "promotionSqlSessionFactory")
    @Primary
    public SqlSessionFactory promotionSqlSessionFactory(@Qualifier("promotionDataSource") DataSource promotionDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(promotionDataSource);
        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver()
                .getResources(promotionMyBatisConfig.MAPPER_LOCATION));
        sessionFactory.setTypeHandlersPackage("com.monkey.mybatis.handler");
        return sessionFactory.getObject();
    }

}
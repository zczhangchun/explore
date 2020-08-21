package com.monkey.redis.config;

import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

import java.util.List;


@Configuration
public class RedisConfig {
    @Value("${spring.redis.tuijianHost}")
    private String tuijianHost;
    @Value("${spring.redis.tuijianPort}")
    private Integer tuijianPort;

    private static final int MAX_ACTIVE = 50;
    private static final int MAX_IDLE = 30;
    private static final int MIN_IDLE = 30;
    private static final long MAX_WAIT = 2000;


    private RedisClusterConfiguration clusterConfiguration(String host, Integer port) {

        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration();
        redisClusterConfiguration.setMaxRedirects(3);

        redisClusterConfiguration.clusterNode(new RedisNode(host, port));
        return redisClusterConfiguration;
    }

    private RedisConnectionFactory connectionFactory(String host, int port, boolean isCluster) {

        LettuceConnectionFactory lettuceConnectionFactory;
        if (!isCluster) {
            lettuceConnectionFactory = new LettuceConnectionFactory(
                    new RedisStandaloneConfiguration(host, port),
                    LettucePoolingClientConfiguration.builder()
                            .poolConfig(getPoolConfig()).build());
        } else {
            lettuceConnectionFactory = new LettuceConnectionFactory(
                    clusterConfiguration(host, port),
                    LettucePoolingClientConfiguration.builder()
                            .poolConfig(getPoolConfig()).build()
            );
        }
        lettuceConnectionFactory.afterPropertiesSet();
        return lettuceConnectionFactory;
    }

    private GenericObjectPoolConfig getPoolConfig() {
        GenericObjectPoolConfig genericObjectPoolConfig = new GenericObjectPoolConfig();
        genericObjectPoolConfig.setMaxTotal(MAX_ACTIVE);
        genericObjectPoolConfig.setMaxIdle(MAX_IDLE);
        genericObjectPoolConfig.setMinIdle(MIN_IDLE);
        genericObjectPoolConfig.setMaxWaitMillis(MAX_WAIT);
        return genericObjectPoolConfig;
    }

    @Bean(name = "tuijianRedisTemplate")
    public StringRedisTemplate tuijianRedisTemplate() {
        return new StringRedisTemplate(connectionFactory(tuijianHost, tuijianPort, false));
    }

    @Bean
    public DefaultRedisScript<List> redisScript(){
        DefaultRedisScript<List> redisScript = new DefaultRedisScript<>();
        redisScript.setResultType(List.class);
        redisScript.setScriptSource(new ResourceScriptSource((new ClassPathResource("redis.lua"))));
        return redisScript;
    }


    @Bean(name = "cacheRedisTemplate")
    public StringRedisTemplate cacheRedisTemplate() {
        return new StringRedisTemplate(connectionFactory("172.16.38.201", 8001, true));
    }

}

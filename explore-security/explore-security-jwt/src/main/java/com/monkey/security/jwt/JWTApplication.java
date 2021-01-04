package com.monkey.security.jwt;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.monkey.security.config")
public class JWTApplication {
}

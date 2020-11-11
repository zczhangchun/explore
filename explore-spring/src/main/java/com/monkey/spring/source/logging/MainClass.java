package com.monkey.spring.source.logging;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.logging.Logger;

/**
 * @author zhangchun
 */
public class MainClass {
    private static Logger logger = Logger.getLogger(MainClass.class.getName());
    public static void main(String[] args) {
        AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext(MainConfig.class);
        logger.info("hello");
        ctx.start();
    }
}

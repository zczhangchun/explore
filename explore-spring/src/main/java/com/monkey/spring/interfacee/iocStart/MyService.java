package com.monkey.spring.interfacee.iocStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class MyService {

    @Autowired
    private MyDao myDao;
}

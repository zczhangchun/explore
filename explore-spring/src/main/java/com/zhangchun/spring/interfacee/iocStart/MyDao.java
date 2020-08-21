package com.zhangchun.spring.interfacee.iocStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class MyDao {

    @Autowired
    private MyService myService;

}

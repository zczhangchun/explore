package com.monkey.spring.interfacee.iocStart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author zhangchun
 */
@Component
public class MyDuplicate {
    @Autowired
    MyDuplicate myDuplicate;
    public void run(){
        System.out.println(myDuplicate);
        myDuplicate.run1();
    }
    public void run1(){
        System.out.println("run1");
    }
}

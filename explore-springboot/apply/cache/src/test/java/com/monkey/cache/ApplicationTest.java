package com.monkey.cache;

import com.monkey.cache.service.HelloService;
import lombok.SneakyThrows;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {

    @Autowired
    private HelloService service;

    @Test
    @SneakyThrows
    public void test1(){
        System.out.println("gg");
        //System.out.println(service.feature(1));
        System.out.println(service.event(2));
        //System.out.println(service.feature(2));
        //System.out.println(service.feature(3));
        //System.out.println(service.feature(1));
        System.out.println(service.event(2));
        System.out.println(service.event(2));
        System.out.println("=========================");
        Thread.sleep(10000);
        //System.out.println(service.feature(1));
        System.out.println(service.event(2));
        //System.out.println(service.feature(1));
        System.out.println(service.event(2));



    }

}

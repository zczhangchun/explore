package com.monkey.mybatis.controller;

import com.monkey.mybatis.mapper.HelloMapper;
import com.monkey.mybatis.model.Info;
import com.monkey.mybatis.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
public class HelloController {

    @Autowired
    HelloMapper helloMapper;
    @Autowired
    HelloService helloService;

    @RequestMapping("baby")
    public Set<String> hell(){
        Set<String> hell = helloMapper.hell();
        return hell;
    }

    @RequestMapping("hello")
    public List<String> hello(){
        List<String> hello = helloMapper.hello(2);

        return hello;
    }


    @RequestMapping("hello2")
    public List<Info> hello2(){
        List<String> list = new ArrayList<>();
        list.add("1");
        list.add("2");
        List<Info> hello = helloMapper.hello2(list, "test", true);

        return hello;
    }
}

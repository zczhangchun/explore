package com.monkey.cache.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class HelloService {

    @Cacheable(value = "promotion", key = "#id")
    public String hello(Integer id){
        return id.toString();
    }


    @Cacheable(value = "promotion", key = "#p0")
    public String hello2(Integer id){
        return id.toString();
    }

    @Cacheable(value = "promotion", key = "#p0 + '_' + #p1 ")
    public String hello3(Integer id, Integer age){
        return id.toString();
    }

    @Cacheable(value = "tag", key = "#id")
    public String hello4(Integer id){
        return id.toString();
    }

    @Cacheable(value = "setting", key = "#id")
    public String hello5(Integer id){
        return id.toString();
    }

}

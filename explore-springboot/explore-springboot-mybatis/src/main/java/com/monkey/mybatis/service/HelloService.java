package com.monkey.mybatis.service;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelloService {

    @Cacheable(value = "promotion", key = "#id")
    public List<String> hello(Integer id){
        List<String> list = new ArrayList<>();
        list.add(id.toString());
        return list;
    }
}

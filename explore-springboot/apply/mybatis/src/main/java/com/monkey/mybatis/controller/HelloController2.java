package com.monkey.mybatis.controller;

import com.monkey.mybatis.mapper.PromotionMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("asd")
public class HelloController2 {


    @Autowired
    private PromotionMapper promotionMapper;

    @RequestMapping("asd")
    public String hell(){
        return "asd";
    }
}

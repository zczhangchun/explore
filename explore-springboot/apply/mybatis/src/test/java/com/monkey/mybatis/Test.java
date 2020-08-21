package com.monkey.mybatis;

import com.monkey.mybatis.mapper.PromotionMapper;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Set;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Test {

    @Autowired
    private PromotionMapper promotionMapper;

    @org.junit.Test
    public void m1(){
        Set<String> query = promotionMapper.query();
        System.out.println(query);
    }
}

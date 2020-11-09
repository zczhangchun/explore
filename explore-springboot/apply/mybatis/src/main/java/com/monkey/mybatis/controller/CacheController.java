package com.monkey.mybatis.controller;

import com.google.common.base.Joiner;
import com.monkey.mybatis.mapper.CacheMapper;
import com.monkey.mybatis.mapper.PromotionMapper;
import com.monkey.mybatis.model.Info;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("com.monkey.cache")
@RestController
public class CacheController {

    @Autowired
    private CacheMapper cacheMapper;
    @Autowired
    private CacheManager caffeineCacheManager;
    @Autowired
    private PromotionMapper promotionMapper;
    @Autowired
    private StringRedisTemplate tuijianRedisTemplate;



    @RequestMapping("hotGame")
    public void hotGame(){

        tuijianRedisTemplate.opsForValue().set("hykb_hotGame", Joiner.on("_").join(promotionMapper.ids()));
    }

    @RequestMapping("queryAll")
    public void queryAll(){

        List<Info> infos = cacheMapper.selectAll();
        Cache cache = caffeineCacheManager.getCache("info");
        for (Info info : infos) {
            cache.put(info.getId()+"_"+1, info);
        }

        System.out.println("gg");
    }


    @RequestMapping("queryOne")
    public Info queryOne(@RequestParam("id") String id){
        Cache cache = caffeineCacheManager.getCache("info");
        Info info = cache.get(84991 + "_" + 1, Info.class);
        return cacheMapper.queryInfoById(id, "tuijian_gameboxGame", true, 1);

    }

    @RequestMapping("queryCache")
    public void queryCache(){
        Cache cache = caffeineCacheManager.getCache("info");
        System.out.println("hello");
    }
}

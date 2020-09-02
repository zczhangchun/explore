package com.monkey.cache.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.LoadingCache;
import com.monkey.cache.A;
import com.monkey.cache.service.HelloService;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@AllArgsConstructor
public class HelloController {

    private final HelloService helloService;

    private final CacheManager cacheManager;

    private final ObjectMapper objectMapper;

    private final A a;

    private final LoadingCache<String, Integer> cache;


    @RequestMapping("getCache")
    public Integer hello1(@RequestParam(value = "key") String key){
        Integer ifPresent = cache.get(key);
        return ifPresent;
    }

    //@RequestMapping("getCache2")
    //public Integer hello1(String key){
    //    //return caffeineCache.get(key, key -> 1);
    //    return 1;
    //}


    @RequestMapping("hello")
    public String hello(){

        //System.out.println(objectMapper);
        a.hello();
        return null;
    }


    @RequestMapping("hello2")
    public void hello2(){
        Cache tagCache = cacheManager.getCache("tag");
        tagCache.put(1, "123");
        tagCache.put(2, "123");
        tagCache.put(3, "123");
        tagCache.put(4, "123");
        System.out.println(tagCache);
        Cache settingCache = cacheManager.getCache("setting");
        settingCache.put(1,"123");
        settingCache.put(2,"123");
        settingCache.put(3,"123");
        settingCache.put(4,"123");
        settingCache.put(5,"123");
        settingCache.put(6,"123");
        System.out.println(settingCache);
    }

    public static void main(String[] args) throws Exception {


        ObjectMapper objectMapper = new ObjectMapper();
        String str = "{\"code\":200,\"msg\":\"OK\",\"data\":{\"title\":\"为你精挑细选\",\"description\":\"好游快爆根据你的游戏品味精心推荐\",\"bg\":\"http://fs.img4399.com/kbyx~sykb/20200522/11202724844\",\"kuaiBaoGameInfoList\":[{\"id\":84698,\"name\":\"王者荣耀体验服\",\"icon\":\"http://fs.img4399.com/sykb~sykb/20200117/14480018015\",\"passthrough\":\"2e2abee3583a4fa5b658c61b94802f20|2\",\"tags\":[\"多人联机\",\"MOBA\",\"竞技\"],\"reason\":\"你可能感兴趣的\\\"多人联机\\\"游戏\",\"advertisementInfo\":{\"token\":\"asdasdasd213323\",\"apkurl\":\"\",\"md5\":\"\",\"channel\":\"\",\"size_byte\":0},\"downinfo\":{\"need_gplay\":false,\"custom_pop2\":0,\"applog\":\"<p>修复bug</p>\",\"sdk_version_name\":\"安卓4.1\",\"icon\":\"http://fs.img4399.com/sykb~sykb/20200117/14480018015\",\"ad_position\":0,\"version\":\"0.54.22.1\",\"min_sdk_version\":16,\"certification\":0,\"official_show\":0,\"apkurl\":\"http://sj.71acg.com/hykb/202008/20200807wzrytyf.apk\",\"size_byte\":\"512796127\",\"size_m\":\"489.04M\",\"appname\":\"王者荣耀体验服\",\"sdk_version\":16,\"appinfo\":\"王者荣耀体验服来自腾讯官方，好游快爆app仅提供信息服务----------------------------------------------------《王者荣耀体验服》是《王者荣耀》对外开放的测试服务器，类似于其他游戏的体验服。任何重要的更新都会在试炼之地优先进行，如新英雄、新道具、新模式等，可以满足您各种的尝鲜需求。进入《王者荣耀体验服》需要专门的客户端，并且QQ帐号或微信账号拥有《王者荣耀体验服》的资格才行。《王者荣耀体验服》目前仅对安卓用户开放。\",\"id\":\"84698\",\"packag\":\"com.tencent.tmgp.sgamece\",\"versioncode\":\"54220102\",\"custom_pop\":0,\"md5\":\"16abf521389e8ad6ffab1d22f8723ac4\",\"status\":\"1\"},\"isAdvertisement\":true}],\"totalNum\":3}}";
        Map<String, Object> map = objectMapper.readValue(str, Map.class);
        System.out.println(map.get("data").getClass());
        System.out.println(map.get("code").getClass());
        System.out.println(map.get("msg").getClass());
    }

}

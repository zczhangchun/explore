package com.monkey.redis.controller;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class RedisController {

    @Autowired
    private StringRedisTemplate tuijianRedisTemplate;
    @Autowired
    private DefaultRedisScript<List> redisScript;
    @Autowired
    private StringRedisTemplate cacheRedisTemplate;

    @RequestMapping("hello3")
    public void hello3(){
        RedissonClient redissonClient = Redisson.create();

    }


    @RequestMapping("hello")
    public List<Double> hello(){
        List<String> list = new ArrayList<>();
        list.add("tj:feature:39485");
        List name = tuijianRedisTemplate.execute(redisScript, list, "download_1d");
        System.out.println(name.size());
        System.out.println(name.get(0).getClass().getTypeName());
        return name;
    }

    @RequestMapping("hello2")
    public void hello2(){

        cacheRedisTemplate.opsForValue().set("item:com.monkey.cache:3:63882", "{id:63681aid:87814tid:47930packag:\"com.tencent.tmgp.cf\"language:\"zh_CN\"size:557476592icopath:\"http://f1.img4399.com/sj~87814_logo_58782bd506597.jpg\"litpic:\"\"version:\"1.0.19.140\"apksource:\"开发商（CPS）\"appdescription:\"\"versioncode:140appinfo:\"《穿越火线:枪战王者》是一款腾讯游戏倾力打造的CF正版FPS手游，完美传承了PC端的品质和玩法，同时还针对手机端的操作特点，进行了针对枪战玩家习惯的定制化适配与优化，让玩家能够在手机上真正享受枪战游戏带来的乐趣和快感，将三亿鼠标的枪战梦想延续到了手机上。\"applog:\"\"screenpath:\"a:5:{i:0;s:53:\"http://f1.img4399.com/sj~opensj_5721a80e39bca?480x800\";i:1;s:45:\"http://f1.img4399.com/sj~opensj_5721a80e4f501\";i:2;s:45:\"http://f1.img4399.com/sj~opensj_5721a80e66f0e\";i:3;s:45:\"http://f1.img4399.com/sj~opensj_5721a80e7b67b\";i:4;s:45:\"http://f1.img4399.com/sj~opensj_5721a80e90fe5\";}\"downurl:\"http://sj.img4399.com/game_list/230/com.tencent.tmgp.cf/tmgp.cf.v136140.apk\"network:0need_gplay:0need_packet:0price:0sdk_version:9md5_file:\"d93d38c4c18460ca6552aedf43fb6d40\"sign:\"8c5f1c0ecf7147e228950e2bc9ab69b1\"sign_common:\"\"devid:10852kind_id:5type_id:1tags:\"58,100,41,56,5,219,226,227,225\"dev:\"a:1:{s:4:\"name\";s:42:\"深圳市腾讯计算机系统有限公司\";}\"versiontime:1493351840pubdate:1493347085video_id:0forums:\"\"jumpurl:\"\"guide_model:\"vivo,oppo,huawei\"tagGames:\"50024,85536,88376,88148,85243\"attr:\"a:1:{s:4:\"tags\";a:3:{i:1;s:20:\"111,38,91,143,56,108\";i:2;s:9:\"138,3,103\";i:3;s:0:\"\";}}\"search_name:\"\"keywords:\"穿越火线挑战王者,穿越火线,枪战,腾讯,CF,cf手游\"pinyin:\"\"writer:\"\"uid:0num_download_box:16273576num_download_correct:0num_download_kuaibao:371646num_comment:0status:1status_ext:0status_ext_info:\"\"ppk_list:\"a:5:{i:0;a:3:{s:3:\"url\";s:0:\"\";s:4:\"size\";s:0:\"\";s:4:\"type\";s:1:\"0\";}i:1;a:3:{s:3:\"url\";s:0:\"\";s:4:\"size\";s:0:\"\";s:4:\"type\";s:1:\"1\";}i:2;a:3:{s:3:\"url\";s:0:\"\";s:4:\"size\";s:0:\"\";s:4:\"type\";s:1:\"2\";}i:3;a:3:{s:3:\"url\";s:0:\"\";s:4:\"size\";s:0:\"\";s:4:\"type\";s:1:\"3\";}i:4;a:3:{s:3:\"url\";s:0:\"\";s:4:\"size\";s:0:\"\";s:4:\"type\";s:1:\"4\";}}\"search_intervene:\"\"video_ids:\"\"realdown:0banhao:\"\"yuyue_num:0area:1version_type:1sensitive_type:1quality:1level:3star:0source:0is_level_hide:0company_id:0company:\"\"banner:\"\"enum_ext:\"\"appname:\"屏蔽开启-热门游戏-变ad广告穿越火线:枪战王者fsd下载\"row_update:\"May 23, 2020 11:08:34 AM\"query_solr:\"屏蔽开启热门游戏变ad广告穿越火线枪战王者fsd下载\"}");
    }
}

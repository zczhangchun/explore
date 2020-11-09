package com.monkey.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {

        //List<Data> list = new ArrayList<>();
        //
        //list.add(new Data("89222", 1, UUID.randomUUID().toString().replace("-","")));
        //list.add(new Data("83294", 2, UUID.randomUUID().toString().replace("-","")));
        //list.add(new Data("60881", 3, UUID.randomUUID().toString().replace("-","")));
        //list.add(new Data("121389", 4, UUID.randomUUID().toString().replace("-","")));
        //ObjectMapper objectMapper = new ObjectMapper();
        //System.out.println(objectMapper.writeValueAsString(list));
        //
        //List<Map<String, Object>> dataList = new ArrayList<>();

        List<User> uList = new ArrayList<>();
        User u1 = new User();
        u1.setAddr("a1;a2;a3;a4;a5");

        User u2 = new User();
        u2.setAddr("b1;b2;b3;b4;b5");

        uList.add(u1);
        uList.add(u2);

        List<String> addrList = uList.stream().map(x -> x.getAddr()).flatMap(x-> Arrays.stream(x.split(";"))).collect(Collectors.toList());
        //或者
        List<String> ridStrList = uList.stream().map(x -> x.getAddr()).map(x -> x.split(";")).flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println(addrList);


    }
}

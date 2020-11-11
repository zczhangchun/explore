package com.monkey.concurrency.apply.demo2.threadLocal;

import java.util.concurrent.TimeUnit;

/**
 * 这里和第一节课里面讲volatile差不多
 */
public class TLDemo1 {

    volatile static String name = "A";

    public static void main(String[] args) {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            name = "B";
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
            System.out.println(name);
        }).start();

    }

}

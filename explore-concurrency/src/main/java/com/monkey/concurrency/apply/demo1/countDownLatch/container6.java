package com.monkey.concurrency.apply.demo1.countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * latch.await()如果设置时间，那么效果就是：
 * 不管latch有没有到0，就会直接执行。
 *
 * 1、让线程都准备好一起执行
 * 2、某个线程需要等其他线程执行完
 */
public class container6 {
    public static void main(String[] args)throws Exception {

        CountDownLatch latch = new CountDownLatch(15);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalId = i;
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(finalId);
                latch.countDown();
            });
        }
        //设置了时间，那么两种情况会结束，一种到达设定时间，一种是latch到达0
        latch.await(2, TimeUnit.SECONDS);
        System.out.println("end");

    }
}

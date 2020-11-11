package com.monkey.concurrency.apply.demo1.cyclicBarrier;

import java.util.concurrent.*;

/**
 * CyclicBarrier
 * 构造方法：
 * public CyclicBarrier(int parties)
 * public CyclicBarrier(int parties, Runnable barrierAction)
 * parties 是参与线程的个数
 * 第二个构造方法有一个 Runnable 参数，这个参数的意思是最后一个到达线程要做的任务
 *
 * CyclicBarrier和CountDownLatch很像。一个是减操作，一个是加操作。
 * CountDownLatch 是一次性的，CyclicBarrier 是可循环利用的。
 *
 */
public class Demo01 {
    public static void main(String[] args)throws Exception {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(15);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalId = i;
            executorService.execute(() -> {
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println(finalId);
                    cyclicBarrier.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }


    }
}

package com.monkey.concurrency.apply.demo1.semaphore;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore的tryAcquire()方法
 * tryAcquire会返回boolean值，即表示有没有抢到坑。
 * 方法里的参数可以设置多个值。
 *
 * tryAcquire()可以设置占用的坑的数量，可以一次占用多个坑。也可以设置时间，代表如果指定时间内没拿到坑，就不等了，返回false。
 *  * release()方法就是释放坑。
 *
 * 下面的例子会看到，只打印了3条数据
 */
public class Demo02 {
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalId = i;
            executorService.execute(() -> {
                try {
                    if(semaphore.tryAcquire(3)){
                        test(finalId);
                        semaphore.release();
                    };
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
        }
        System.out.println("end");
        executorService.shutdown();

    }

    public static void test(int k) throws Exception {
        Thread.sleep(1000);
        System.out.println("k ------" + k);
    }
}

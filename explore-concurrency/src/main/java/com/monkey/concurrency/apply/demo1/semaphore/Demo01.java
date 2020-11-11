package com.monkey.concurrency.apply.demo1.semaphore;

import java.util.concurrent.*;

/**
 * Semaphore的简单应用
 * 可以把这个Semaphore看作是一个坑。
 * Semaphore可以设置有坑的数量。
 * 所有线程来抢坑，当坑都被占用后，其他线程等待。
 *
 * acquire可以设置占用的坑的数量，可以一次占用多个坑。
 * release()方法就是释放坑。
 *
 * 下面的例子会看到，每次打印都是3个3个打印的。
 *
 */
public class Demo01 {
    public static void main(String[] args) {

        //设置坑的数量
        Semaphore semaphore = new Semaphore(3);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            final int finalId = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    test(finalId);
                    semaphore.release();
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

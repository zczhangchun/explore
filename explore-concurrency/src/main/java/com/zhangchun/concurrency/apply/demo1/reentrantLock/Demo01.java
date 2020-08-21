package com.zhangchun.concurrency.apply.demo1.reentrantLock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo01 {
    public static void main(String[] args)throws Exception {

        Lock lock = new ReentrantLock(true);
        Thread t1 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(100000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t1.start();
        Thread.sleep(500);

        Thread t2 = new Thread(() -> {
            lock.lock();
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                lock.unlock();
            }
        });
        t2.start();
        lock.lockInterruptibly();

    }
}

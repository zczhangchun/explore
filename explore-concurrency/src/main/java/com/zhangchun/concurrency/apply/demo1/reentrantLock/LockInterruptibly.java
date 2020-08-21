package com.zhangchun.concurrency.apply.demo1.reentrantLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 如果我们用lock.lock()上锁，那么使用 线程对象.interrupt()，是打断不了线程的。
 * 如果想让reetrantLock能够被打断，
 * 就需要使用lock.lockInterruptibly。
 * 这样 线程对象.interrupt()才能够打断lock
 *
 *
 */
public class LockInterruptibly {

    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();

        Thread t1 = new Thread(()->{
            try {
                lock.lock();
                System.out.println("t1 start");
                TimeUnit.SECONDS.sleep(Integer.MAX_VALUE);
                System.out.println("t1 end");
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                lock.unlock();
            }
        });
        t1.start();

        Thread t2 = new Thread(()->{
            boolean locked = false;
            try {
//                lock.lock();
                //使用lockInterruptibly来锁定可以对Interrupt方法作出响应
                //不使用这个方法，永远拿不到锁，就永远阻塞住，使用了之后，可以执行catch里的代码，将线程结束
                lock.lockInterruptibly();
                System.out.println("t2 start");
                TimeUnit.SECONDS.sleep(5);
                System.out.println("t2 end");
                locked = true;
            } catch (InterruptedException e) {
                System.out.println("interrupted!");
            } finally {
                if (locked){
                    lock.unlock();
                }
            }
        });
        t2.start();

        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        t2.interrupt();
    }

}

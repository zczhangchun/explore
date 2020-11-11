package com.monkey.concurrency.apply.demo2.threadLocal;

import java.util.concurrent.TimeUnit;

/**
 * ThreadLocal 线程局部变量
 * threadlocal只对当前线程范围有效，比如下面例子，在第一个线程设置的值，第二个线程是使用不了的。
 */
public class TLDemo2 {

    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    public static void main(String[] args) {

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (Exception e) {
                e.printStackTrace();
            }
            threadLocal.set(new User());
            System.out.println(threadLocal.get());
        }).start();

        new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {
                e.printStackTrace();
            }
//            threadLocal.set(new User());
//            threadLocal.remove();
            System.out.println(threadLocal.get());
        }).start();

    }

}

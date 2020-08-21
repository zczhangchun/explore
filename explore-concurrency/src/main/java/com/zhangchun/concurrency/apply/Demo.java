package com.zhangchun.concurrency.apply;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Demo {
    public static void main(String[] args)throws Exception {

        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        new Thread(() -> {
            readWriteLock.readLock().lock();

        }).start();
        Thread thread = new Thread(() -> {
            readWriteLock.readLock().lock();
            readWriteLock.readLock().unlock();
            readWriteLock.readLock().lock();
        });

        thread.start();





        String a;
        String b;
        String c;
//        a = b = c();
        if (true){
            a = "c";
        }else if (true){
            a = "b";
        }

        System.out.println(a);

    }

    public static String c(){
        return "c";
    }
}

package com.monkey.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

/**
 * @author zhangchun
 * 多个线程交替执行依然是轻量锁，只要不存在竞争就不会升级成重量锁
 * 使用参数将延迟关闭
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class JOLExample14 {
    static A a;
    public static void main(String[] args)throws Exception {
        a = new A();
        System.out.println("before lock");
        System.out.println("无锁：" + ClassLayout.parseInstance(a).toPrintable());//无锁，或者叫"可偏向锁"
        Thread t1 = new Thread(() -> {
            synchronized (a){
                System.out.println("t1 release");
                System.out.println("t1 "+ ClassLayout.parseInstance(a).toPrintable());//偏向锁
            }
        });
        t1.start();
        t1.join();
        Thread t2 = new Thread(() -> {
            synchronized (a){
                System.out.println("t2 release");
                System.out.println("t2 "+ ClassLayout.parseInstance(a).toPrintable());//轻量锁？偏向锁：都有可能，具体看JOLExample12
            }
        });
        t2.start();
        t2.join();
        Thread t3 = new Thread(() -> {
            synchronized (a){
                System.out.println("t3 release");
                System.out.println("t3 "+ ClassLayout.parseInstance(a).toPrintable());//轻量锁？偏向锁：都有可能，具体看JOLExample12
            }
        });
        t3.start();
        t3.join();
        System.gc();
        System.out.println("after gc()");
        System.out.println(ClassLayout.parseInstance(a).toPrintable());


    }
}

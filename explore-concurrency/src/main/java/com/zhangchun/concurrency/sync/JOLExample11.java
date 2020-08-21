package com.zhangchun.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/**
 * 证明wait会直接将锁变成重量级锁的代码
 * 在after wait打印出来的锁是重量级锁（010）
 */
public class JOLExample11 {
    static A a;
    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        a = new A();
        out.println("befre lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        Thread t1= new Thread(){
            public void run() {
                synchronized (a){
                    try {
                        synchronized (a) {
                            System.out.println("before wait");
                            out.println(ClassLayout.parseInstance(a).toPrintable());
                            a.wait();
                            System.out.println(" after wait");
                            out.println(ClassLayout.parseInstance(a).toPrintable());
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(7000);
        synchronized (a) {
            a.notifyAll();
        }
    }
}
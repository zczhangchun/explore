package com.zhangchun.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * @author zhangchun
 * 证明
 * 到达20次是批量重偏向
 * 到达40次之后是批量撤销（也叫批量变轻量锁）
 * 关闭偏向锁延迟
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class JOLExample15 {
    static List<A> list = new ArrayList<A>();
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i=0;i<100;i++){
                A a = new A();
                synchronized (a){
                    list.add(a);
                }
            }
            out.println("t1结束");
        });
        t1.start();
        t1.join();
        out.println("befre t2");
        out.println(ClassLayout.parseInstance(list.get(1)).toPrintable());
        Thread t2 = new Thread() {
            int k=0;
            public void run() {
                for(A a:list){
                    synchronized (a){
                        if (k == 21){
                            out.println("t2 ing：" + k );
                            //偏向锁，因为偏向锁撤销次数过了阈值20，就批量重偏向了
                            out.println(ClassLayout.parseInstance(a).toPrintable());//偏向锁
                        }
                    }
                    k++;
                }

            }
        };
        t2.start();
        t2.join();
        Thread t3 = new Thread() {
            int k=0;
            public void run() {
                for(A a:list){
                    synchronized (a){
                        if (k == 21){
                            out.println("t2 ing：" + k );
                            //虽然偏向锁撤销操作次数过了阈值20，但是却是轻量锁，why？
                            //当阈值超过20是批量重偏向没错，但是之前t2执行了20次，这次又执行了20次，总共就是40次。
                            //当偏向锁撤销操作次数到了阈值40次之后，就不是批量重偏向了，而是批量撤销（或者说是批量变成轻量锁，把剩下的对象全部变成轻量锁）
                            out.println(ClassLayout.parseInstance(a).toPrintable());//轻量锁
                        }
                    }
                    k++;
                }

            }
        };
        t3.start();
    }
}

package com.zhangchun.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/**
 * @author zhangchun
 * 证明偏向锁是不会单个重新偏向的。
 * 单个重偏向和批量重偏向的含义是截然不同的。
 * 需要使用参数将偏向锁延迟关闭
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class JOLExample12 {
    static A a;
    public static void main(String[] args)throws Exception {
        a = new A();
        Thread t1 = new Thread(() -> {
            synchronized (a){
                //此时是偏向锁101
                out.println(ClassLayout.parseInstance(a).toPrintable());
            }
        });
        t1.start();
        //让上面的线程执行完
        t1.join();
        new Thread(() -> {
            synchronized (a){
                /*
                  之前已经是偏向锁，因为偏向锁不会重新偏向的原因，所以这次会变成轻量级锁000
                  但是！！真的就一定是轻量锁吗？其实不一定，有可能还是偏向锁，但是偏向的还是上面的线程。（有几率的）
                  为什么出现这样的情况，其实跟硬件有关，查看他们打印的线程ID，发现线程ID还是一模一样的。
                  原因：在t1销毁后，立马创建t2，t2的线程ID刚好是t1的线程ID，
                  sync只认线程ID，不认hashCode什么的，所以会以为是同一个线程，那么偏向的是同一个线程ID，就不需要升级成轻量锁了。
                  所以！！我们的理论：偏向锁是不回单个重偏向的。只会批量偏向。
                 */
                out.println(ClassLayout.parseInstance(a).toPrintable());
            }
        }).start();
    }
}

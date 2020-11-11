package com.monkey.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/**
 * 偏向锁的延迟证明
 * 使用参数来控制偏向锁和轻量锁，这个参数会让偏向锁延迟关闭（JVM在启动的时候，如果有用到偏向锁的地方会直接用轻量锁代替，为了节省性能，具体看笔记）
 * 何为"可偏向锁"？即虽然是偏向锁，但还没有偏向任何线程ID
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 查看关闭了延迟和不关闭延迟的对象头的锁的字节
 *
 */
public class JOLExample3 {
    static A a;
    public static void main(String[] args) throws Exception {
        a = new A();
        out.println("befor lock");
        //如果我们使用了hashCode，此时就不会是"可偏向锁"，而是一个无锁。因为无锁的对象头和可偏向锁的对象头是不同的
        //无锁的对象头会存储hashCode，可偏向锁存储的是线程ID，所以调用了hashCode后，就存放了hashCode，就不能再存放线程ID了，就是"无锁"了
        //打开或者关闭下面代码打印出来的效果
//        a.hashCode();
        out.println(ClassLayout.parseInstance(a).toPrintable());//无锁？偏向锁？
        synchronized (a) {
            out.println("lock ing");
            //原本此时只是偏向锁，但是如果此时在这里计算了hashCode，那么就会变成重量锁
//            a.hashCode();
             out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

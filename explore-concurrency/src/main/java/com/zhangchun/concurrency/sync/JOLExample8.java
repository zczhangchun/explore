package com.zhangchun.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/**
 * 也是证明偏向锁的延迟
 */
public class JOLExample8 {
   static A a;
    public static void main(String[] args) throws Exception {
        Thread.sleep(5000);
        a= new A();
        a.hashCode();
        out.println("befor lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
        synchronized (a){
            out.println("lock ing");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

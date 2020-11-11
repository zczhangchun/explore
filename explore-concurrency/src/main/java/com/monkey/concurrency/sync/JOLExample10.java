package com.monkey.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;
import static java.lang.System.out;

/**
 * 证明两个线程如果是交替执行的话，那么就依然是轻量级锁，而不是重量级锁。
 * "进程>2就是重量级锁" 这句话是错的，如果他们都是交替执行，那么依然是轻量级锁。
 * 如果只有两个线程，但是他们竞争资源，那么就是重量级锁
 */
public class JOLExample10 {
   static A a;
    public static void main(String[] args) throws Exception {
        a= new A();
        Thread t1 = new Thread(){
            @Override
            public void run() {
                synchronized (a){
                    out.println("t1 lock ing");
                    out.println(ClassLayout.parseInstance(a).toPrintable());
                }
            }
        };
        t1.start();
        //睡眠是为了让上面的打印语句能够执行完，上面的打印语句会稍微慢一些
       Thread.sleep(6000);
        synchronized (a){//a b c c+++
            out.println("main lock ing");
            out.println(ClassLayout.parseInstance(a).toPrintable());
        }

//        Thread thread2 = new Thread(){
//            @Override
//            public void run() {
//
//                synchronized (a){
//                    out.println("t2 lock ing");
//                    out.println(ClassLayout.parseInstance(a).toPrintable());
//                }
//            }
//        };
//        thread2.start();
//        Thread.sleep(10);
//        synchronized (a){
//            out.println("main lock ing");
//            out.println(ClassLayout.parseInstance(a).toPrintable());
//        }
        out.println("after lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

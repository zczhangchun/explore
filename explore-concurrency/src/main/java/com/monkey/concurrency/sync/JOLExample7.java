package com.monkey.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;
import static java.lang.System.out;

/**
 * 锁的升级证明
 */
public class JOLExample7 {
    static A a;
    public static void main(String[] args) throws Exception {
        //Thread.sleep(5000);
        a = new A();
        out.println("befre lock");
        out.println(ClassLayout.parseInstance(a).toPrintable());//无锁
        Thread t1= new Thread(){
            public void run() {
                synchronized (a){
                    try {
                        Thread.sleep(5000);
                        System.out.println("t1 release");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        t1.start();
        Thread.sleep(1000);
        out.println("t1 lock ing");
        out.println(ClassLayout.parseInstance(a).toPrintable());//轻量锁
        sync();
        out.println("after lock");
        //实际上是无锁，但是打印的是重量锁
        //原因：锁在被使用后，都会被释放，此时都是无锁状态。但是我们打印看到的是重量锁，所以不要被误导。实际上就是无锁
        out.println(ClassLayout.parseInstance(a).toPrintable());
        System.gc();
        out.println("after gc()");
        out.println(ClassLayout.parseInstance(a).toPrintable());//无锁---gc
    }
    public  static  void sync() throws InterruptedException {
        synchronized (a){
            System.out.println("t1 main lock");
            out.println(ClassLayout.parseInstance(a).toPrintable());//重量锁
        }
    }
}

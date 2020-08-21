package com.zhangchun.concurrency.sync;

import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

public class SummaryLockJOLExample {
    public static void main(String[] args)throws Exception {
        //睡眠6秒，保证可以用偏向锁
        Thread.sleep(6000);

        final List<A> list1 = new ArrayList<A>();

        List<B> list2 = new ArrayList<B>();
        //对两个数组添加对象
        for (int i = 0; i < 100; i++) {
            list1.add(new A());
            list2.add(new B());
        }

        Thread t1 = new Thread(){
            String name = "1";

            @Override
            public void run() {
                System.out.println(name);
                for (A a : list1) {
                    synchronized (a){
                        if (a == list1.get(10)){
                            System.out.println("t1线程 -> 预期是偏向锁" + 10 + ClassLayout.parseInstance(a).toPrintable());
                        }
                    }
                }
                try {
                    //保证其他线程的任务可以执行完
                    Thread.sleep(100000);
                }catch (InterruptedException e){
                    e.printStackTrace();
                }

            }
        };
        t1.start();
        //保证t1的for循环可以执行完
        Thread.sleep(5000);
        System.out.println("main线程 -> 预期是偏向锁，同步结束后不撤销盘香锁，在下次使用的时候进行撤销偏向并膨胀为轻量级锁或者重锁 或重偏向" + ClassLayout.parseInstance(list1.get(10)).toPrintable());

        Thread t2 = new Thread(){
            String name = "2";

            @Override
            public void run() {
                System.out.println(name);

                for (int i = 0; i < 100; i++) {
                    //到这一行代码，集合里的所有对象都是偏向锁，偏向t1
                    A a = list1.get(i);
                    if (i == 20){
                        //为了证明最后一步的if (i == 20)，再加一次锁
                        a = list1.get(9);
                    }
                    synchronized (a){
                        if (a == list1.get(10)){
                            System.out.println("t2线程 -> get(1)预期是无锁，因为t1被用过了，从偏向锁升级成轻量锁，用完释放后此时就是无锁" + ClassLayout.parseInstance(list1.get(i)).toPrintable());
                            System.out.println("t2线程 -> get(10)预期轻量级锁" + i + ClassLayout.parseInstance(a).toPrintable());

                        }
                        if (a == list1.get(19)){
                            System.out.println("t2线程 -> i=19 get(10)预期是无锁，不可偏向" + 10 + ClassLayout.parseInstance(list1.get(10)).toPrintable());
                            System.out.println("t2线程 -> get(19) 满足重偏向条件20 预期偏向锁，偏向t2" + i + ClassLayout.parseInstance(a).toPrintable());
                            System.out.println("t2线程 -> i=19 get(40)未同步到的对象，依然偏向t1，打破了我们之前说的是批量重偏向,具体是为什么不清楚" + i + ClassLayout.parseInstance(list1.get(40)).toPrintable());
                        }
                        if (i == 20){
                            System.out.println("t2线程 -> i=20 get(9) 预期是轻量级锁，因为无锁 不可偏向的表示不可重新更改为可偏向状态，因此会直接升级成轻量锁" + ClassLayout.parseInstance(list1.get(9)).toPrintable());
                        }
                    }
                }
            }
        };
        t2.start();


    }
}

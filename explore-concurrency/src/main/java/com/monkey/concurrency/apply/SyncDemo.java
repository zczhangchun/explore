package com.monkey.concurrency.apply;

/**
 * @author zhangchun
 */
public class SyncDemo {
    Object o = new Object();

    public static void main(String[] args) {
        SyncDemo syncDemo = new SyncDemo();
        syncDemo.start();
    }

    public void start() {
        Thread thread1 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        Thread thread2 = new Thread() {
            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(500);
                        sync();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread1.setName("t1");
        thread2.setName("t2");
        thread1.start();
        thread2.start();
    }

    public void sync() throws InterruptedException {
        synchronized (o) {
            System.out.println(Thread.currentThread().getName());
        }
    }
}

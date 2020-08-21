package com.zhangchun.concurrency.apply;

/**
 * @author zhangchun
 */
public class BadThread2 {
    private static Thread thread = null;
    private static boolean running = true;
    public static void main(String[] args)throws Exception {
        transaction();
//        Thread.sleep(100);
        running = true;
    }
    public static void transaction(){
        thread = new Thread(){
            @Override
            public void run() {
                while (running){
                }
            }
        };
        thread.start();
    }
}

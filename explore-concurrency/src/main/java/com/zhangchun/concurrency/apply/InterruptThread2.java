package com.zhangchun.concurrency.apply;

/**
 * @author zhangchun
 */
public class InterruptThread2 {
    private static Thread thread = null;
    private static boolean running = true;
    public static void main(String[] args)throws Exception {
        transaction();
        Thread.sleep(100);
        //优雅
        thread.interrupt();
    }
    public static void transaction(){
        thread = new Thread(){
            @Override
            public void run() {
                while (!this.isInterrupted()){
                }
            }
        };
        thread.start();
    }
}

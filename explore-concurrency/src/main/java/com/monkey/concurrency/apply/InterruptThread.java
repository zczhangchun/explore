package com.monkey.concurrency.apply;

/**
 * @author zhangchun
 */
public class InterruptThread {
    private static Thread thread = null;
    private static boolean running = true;
    static boolean flag;
    public static void main(String[] args)throws Exception {
        transaction();
        Thread.sleep(100);
        //优雅
        thread.interrupt();
//        System.out.println(flag);

    }
    public static void transaction(){
        thread = new Thread(){
            @Override
            public void run() {
                while (!( flag = this.isInterrupted())){
                }
                System.out.println(this.isInterrupted());
                System.out.println(Thread.interrupted());
                System.out.println(this.isInterrupted());
                System.out.println(Thread.interrupted());System.out.println(this.isInterrupted());
                System.out.println(Thread.interrupted());
            }
        };
        thread.start();
    }
}

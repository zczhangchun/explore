package com.monkey.concurrency.apply;

/**
 * @author zhangchun
 */
public class BadThread1 {
    private static Thread thread = null;
    private static boolean running = true;
    public static void main(String[] args)throws Exception {
        transaction();
        Thread.sleep(100);
        //不优雅，这样可能thread来不及执行剩下代码（剩下代码可能会释放资源）
        thread.stop();
    }
    public static void transaction(){
        thread = new Thread(){
            @Override
            public void run() {
                while (running){
                    //开启资源
                    //xxx.open()
//                    try {
//                        Thread.sleep(10000);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                    //关闭资源
                    //xxx.close()
                }
            }
        };
        thread.start();
    }
}

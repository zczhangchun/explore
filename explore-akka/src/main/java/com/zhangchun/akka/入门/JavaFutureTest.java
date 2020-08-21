package com.zhangchun.akka.入门;

import java.util.concurrent.*;

public class JavaFutureTest {

    static class ThreadDemo extends Thread{
        @Override
        public void run() {
            System.out.println("run...");
        }
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(4);

        Future<String> future = executorService.submit(new Callable<String>() {

            @Override
            public String call() throws Exception {
                throw new NullPointerException();
//                return "hello";
            }
        });

        try {
            String result = future.get();

            System.out.println(result);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            System.out.println("null exception");
        } catch (ExecutionException e) {
            e.printStackTrace();
        }


    }
}

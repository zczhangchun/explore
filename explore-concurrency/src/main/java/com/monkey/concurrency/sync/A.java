package com.monkey.concurrency.sync;

public class A implements Cloneable{
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    int i=0;
    int b = 2;
    int c = 3;
//    String d = "1";
    long d = 1;
    public synchronized void parse(){
        i++;
//        JOLExample6.countDownLatch.countDown();
    }
}

package com.monkey.concurrency.sync;

/**
 * 偏向锁和轻量锁的执行时间测试代码
 * 使用参数来控制偏向锁和轻量锁，这个参数会让偏向锁延迟关闭（JVM在启动的时候，如果有用到偏向锁的地方会直接用轻量锁代替，为了节省性能，具体看笔记）
 * 何为"可偏向锁"？即虽然是偏向锁，但还没有偏向任何线程ID
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 */
public class JOLExample4 {
    public static void main(String[] args) throws Exception {
        A a = new A();
        long start = System.currentTimeMillis();
        //调用同步方法1000000000L 来计算1000000000L的++，对比偏向锁和轻量级锁的性能
        //如果不出意外，结果灰常明显
        for(int i=0;i<1000000000L;i++){
            a.parse();
        }
        long end = System.currentTimeMillis();
        System.out.println(String.format("%sms", end - start));
    }
}

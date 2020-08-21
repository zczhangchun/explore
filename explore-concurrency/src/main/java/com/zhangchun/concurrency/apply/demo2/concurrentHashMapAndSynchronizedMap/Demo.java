package com.zhangchun.concurrency.apply.demo2.concurrentHashMapAndSynchronizedMap;

import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.CountDownLatch;

/**
 * ConcurrentHashMap
 * 并发的hashmap，这个例子测试一下效率
 *
 * 第一种用hashtable，hashtable所有方法都加了锁了，第二种concurrentHashMap，
 * 大致能看出来他的效率要比hashtable要高一些，在多线程的情况下。
 * 为什么呢，因为hashtable往里面加任何数据的时候都是要锁定整个对象，
 * 而concurrentHashMap，是分成十六个段，每次插数据的时候，只会锁住一小段,1.8之后实现不同。
 *
 * ConcurrentSkipListMap是TreeMap的线程安全的实现
 */
public class Demo {

	public static void main(String[] args) {
//		Map<String, String> map = new ConcurrentHashMap<>();
		Map<String, String> map = new ConcurrentSkipListMap<>();
//		Map<String, String> map = new Hashtable<>();

//		Map<String, String> map = new HashMap<>();
//		Map<String, String> map1 = Collections.synchronizedMap(map);

		Random random = new Random();
		Thread[] threads = new Thread[100];
		CountDownLatch latch = new CountDownLatch(threads.length);
		long start_time = System.currentTimeMillis();
		for (int i = 0; i < threads.length; i++) {
			threads[i] = new Thread(()->{
				for(int j=0; j<10000;j++) {
					map.put("a" + random.nextInt(100000), "a" + random.nextInt(100000));
//					map1.put("a" + random.nextInt(100000), "a" + random.nextInt(100000));
				}
				latch.countDown();
			});
		}
		Arrays.asList(threads).forEach(t->t.start());
		try {
			latch.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		long end_time = System.currentTimeMillis();
		System.out.println(end_time-start_time);
	}
}

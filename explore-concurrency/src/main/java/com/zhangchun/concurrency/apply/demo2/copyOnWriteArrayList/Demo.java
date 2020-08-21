package com.zhangchun.concurrency.apply.demo2.copyOnWriteArrayList;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * CopyOnWriteList 写时复制容器
 *
 * 在往集合中添加数据的时候，先拷贝存储的数组，然后添加元素到拷贝好的数组中，
 * 然后用现在的数组去替换成员变量的数组（就是get等读取操作读取的数组）。
 * 这个机制和读写锁是一样的，但是比读写锁有改进的地方，那就是读取的时候可以写入的 ，
 * 这样省去了读写之间的竞争，看了这个过程，你也发现了问题，同时写入的时候怎么办呢，当然果断还是加锁。
 *
 * 读多写少可以用copyOnWriteList
 *
 * 下面例子看看效率
 */
public class Demo {

	public static void main(String[] args) {
//		List<String> lists = new ArrayList<>();
//		List<String> lists = new Vector<>();
		List<String> lists = new CopyOnWriteArrayList<>();
		Random r = new Random();
		Thread[] threads = new Thread[100];
		
		for (int i = 0; i < threads.length; i++) {
			Runnable task = new Runnable() {
				@Override
				public void run() {
					for (int j = 0; j < 1000; j++) {
						lists.add("A" + r.nextInt(10000));
					}
				}
			};
			threads[i] = new Thread(task);
		}
		
		run(threads);
		
		System.out.println(lists.size());
	}

	private static void run(Thread[] threads) {
		long start = System.currentTimeMillis();
		Arrays.asList(threads).forEach(t->t.start());
		Arrays.asList(threads).forEach(t->{
			try {
				t.join();
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
		long end = System.currentTimeMillis();
		System.out.println(end - start);
	}
}

package com.zhangchun.concurrency.apply.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 单线程线程池
 * 这个线程池里永远只有一个线程
 * 这个作用就是在单线程下，执行的顺序是整齐的。
 * 保证任务有顺序。
 */
public class Demo9_SingleThreadPool {

	public static void main(String[] args) {
		ExecutorService service = Executors.newSingleThreadExecutor();
		
		for (int i = 0; i < 5; i++) {
			final int j = i;
			service.execute(()->{
				System.out.println(j + " " + Thread.currentThread().getName());
			});
		}
	}
}

package com.monkey.concurrency.apply.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 缓存线程池
 * 一开始创建它，线程池里没有线程。只有当有任务的时候才会创建线程。
 * 当没有空闲线程时，则创建线程。有空闲线程时，就让空闲线程执行任务。
 * 创建的线程都会放在池里。当一个空闲线程空闲了60秒后，他就会被回收掉，不会占用资源
 */
public class Demo8_CachedPool {

	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newCachedThreadPool();
		System.out.println(service);
		
		for (int i = 0; i < 2; i++) {
			service.execute(()->{
				try {
					TimeUnit.MILLISECONDS.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		
		System.out.println(service);

		TimeUnit.SECONDS.sleep(60);
		System.out.println(service);

		//让线程空闲61秒后，在查看会发现池里的线程都被销毁了。
		TimeUnit.SECONDS.sleep(1);
		System.out.println(service);
	}
}

package com.zhangchun.concurrency.apply.demo3;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 定时线程池
 * 可以以固定的时间来执行任务
 * 可以用来做定时任务用的
 */
public class Demo10_ScheduledPool {

	public static void main(String[] args) {
		ScheduledExecutorService service = Executors.newScheduledThreadPool(4);

		//四个参数（要执行的任务，启动多长时间后执行，每隔多长时间来执行，时间单位）
		service.scheduleAtFixedRate(()->{
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println(Thread.currentThread().getName());
		}, 0, 500, TimeUnit.MILLISECONDS);
	}
}

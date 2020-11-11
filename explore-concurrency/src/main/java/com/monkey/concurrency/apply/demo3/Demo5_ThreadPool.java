package com.monkey.concurrency.apply.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 线程池的概念
 * 运行状态
 * java.util.concurrent.ThreadPoolExecutor@6f496d9f[Running, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
 * 关闭状态，但是还没有真正停止，要等任务完成了才停止
 * java.util.concurrent.ThreadPoolExecutor@6f496d9f[Shutting down, pool size = 5, active threads = 5, queued tasks = 1, completed tasks = 0]
 * 停止状态
 *	java.util.concurrent.ThreadPoolExecutor@6f496d9f[Terminated, pool size = 0, active threads = 0, queued tasks = 0, completed tasks = 6]
 *
 * pool size：线程池大小
 * active threads：活跃的线程数量（没去干活的线程数量）
 * queued tasks：队列中存放的任务数量（当有6个任务，线程池只有5个，每个线程执行一个任务，就会有一个任务暂时不会被执行，就会被放到队列中）
 * completed：完成的任务数量
 *
 * 线程池：有一个队列和一个池。交给线程池任务的时候，是直接把任务交给线程池里的线程，当没有空闲线程了，才把任务放到队列中。（注意顺序，先给线程，不够再放队列）
 * 线程池大小最少设置成CPU的核数+1：因为太少了耗费资源
 */
public class Demo5_ThreadPool {

	public static void main(String[] args) throws InterruptedException {
		//newFixedThreadPool -> 创建一个固定大小的线程池
		//ExecutorService是一个服务，我们把任务丢进去他就会去执行，并且他会一直存在，一直提供服务，等待我们把任务丢进去
		ExecutorService service = Executors.newFixedThreadPool(5);
		//丢6个任务进去
		for (int i = 0; i < 6; i++) {
			service.execute(()->{
				try {
					TimeUnit.MICROSECONDS.sleep(500);
				} catch (Exception e) {
					e.printStackTrace();
				}
				System.out.println(Thread.currentThread().getName());
			});
		}
		
		System.out.println(service);

		//马上停止线程池
//		service.shutdownNow();
		//等任务全部执行完再关闭线程池
		service.shutdown();
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);

		TimeUnit.SECONDS.sleep(5);
		System.out.println(service.isTerminated());
		System.out.println(service.isShutdown());
		System.out.println(service);
	}
}

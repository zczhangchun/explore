package com.zhangchun.concurrency.apply.demo3;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * 工作窃取线程池
 * 这个线程池里的线程都是守护线程
 * 简而言之：每个线程一空下来就会找活干
 * 每个线程维护一个任务队列，现在我们是16核，就有16个线程，然后分配17个任务。
 * 其中有一个线程有2个任务，当别的线程如果先执行完了，发现那个线程还有任务，就会过去把它的活做了
 */
public class Demo11_WorkStealingPool {

	public static void main(String[] args) throws InterruptedException {
		//根据CPU核心数来决定线程数量，我们有16个核
		ExecutorService service = Executors.newWorkStealingPool();
		System.out.println(Runtime.getRuntime().availableProcessors());

		//17个任务
		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(1000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		service.execute(new R(2000));
		//因为newWorkStealingPool池里的线程都是守护线程，守护线程在所有线程停止了，不管守护线程任务有没有执行完，守护线程都会被停止
		//所以这里要睡眠，让守护线程把任务执行了，我们才能看到打印结果
		TimeUnit.SECONDS.sleep(90);
	}
	
	static class R implements Runnable{

		int time;
		
		public R (int t){
			this.time = t;
		}

		//R就是睡眠时间
		@Override
		public void run() {
			try {
				TimeUnit.MILLISECONDS.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println(time + " " + Thread.currentThread().getName());
		}
		
	}
}

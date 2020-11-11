package com.monkey.concurrency.apply.demo3;

import java.util.concurrent.*;

/**
 * 认识future(将来)
 * 就是线程去执行任务，在未来的某一时刻执行完任务后，会返回结果（这也是callable）
 */
public class Demo6_Future {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		FutureTask<Integer> task = new FutureTask<>(()->{//new callable
			TimeUnit.MILLISECONDS.sleep(500);
			return 1000;
		});
		
		new Thread(task).start();
		//get()会阻塞，等到线程返回结果后，获取结果，然后才不阻塞
		System.out.println(task.get());
		
		ExecutorService service = Executors.newFixedThreadPool(5);
		//submit返回的是一个Future类型，submit可以用来执行那些需要有返回值的任务
		Future<Integer> f = service.submit(()->{//new callable
			TimeUnit.MILLISECONDS.sleep(500);
			return 1;
		});
		//get()会阻塞，等到线程执行完任务，任务返回结果后，获取结果，然后才不阻塞
		System.out.println(f.get());
		//isDone()方法返回的任务是否执行完，执行完就返回true。
		System.out.println(f.isDone());
	}

}

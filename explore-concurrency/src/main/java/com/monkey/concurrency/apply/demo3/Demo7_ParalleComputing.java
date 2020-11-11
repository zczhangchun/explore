package com.monkey.concurrency.apply.demo3;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

/**
 * 线程池的概念
 * 并行处理，这里是我们手动将任务分配成4个小任务的。
 * 假设要找1～20万之间的质数，可以把这一个大任务分成4个任务，每个任务去找5万个数中的质数，然后4个线程同时去执行，最后4个线程将结果汇总成一个
 * 这样效率才能够提高
 */
public class Demo7_ParalleComputing {

	public static void main(String[] args) throws InterruptedException, ExecutionException {
		long start = System.currentTimeMillis();
		//一个大任务
		List<Integer> results = getPrime(1, 200000);
		long end = System.currentTimeMillis();
		//大任务执行的时间
		System.out.println(end - start);

		final int cupCoreNum = 8;

		ExecutorService service = Executors.newFixedThreadPool(cupCoreNum);

		//4个小任务
		MyTask myTask1 = new MyTask(1, 50000);
		MyTask myTask2 = new MyTask(50001, 100000);
		MyTask myTask3 = new MyTask(100001, 150000);
		MyTask myTask4 = new MyTask(150001, 200000);

		Future<List<Integer>> f1 = service.submit(myTask1);
		Future<List<Integer>> f2 = service.submit(myTask2);
		Future<List<Integer>> f3 = service.submit(myTask3);
		Future<List<Integer>> f4 = service.submit(myTask4);
		
		start = System.currentTimeMillis();
		f1.get();
		f2.get();
		f3.get();
		f4.get();
		end = System.currentTimeMillis();
		//4个小人物执行的时间
		System.out.println(end - start);
		service.shutdown();
	}
	
	static class MyTask implements Callable<List<Integer>> {
		int startPos, endPos;
		
		public MyTask(int s, int e) {
			this.startPos = s;
			this.endPos = e;
		}
		@Override
		public List<Integer> call() throws Exception {
			List<Integer> r = getPrime(startPos, endPos);
			return r;
		}
	}
	//判断一个数是不是质数
	static boolean isPrime(int num){
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}
	//获取start-end之间的所有质数
	static List<Integer> getPrime(int start, int end){
		List<Integer> results = new ArrayList<Integer>();
		for (int i = start; i <= end; i++) {
			if (isPrime(i) && i !=1) {
				results.add(i);
			}
		}
		return results;
	}
	
}

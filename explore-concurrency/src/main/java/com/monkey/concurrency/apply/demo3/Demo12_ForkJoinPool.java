package com.monkey.concurrency.apply.demo3;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * ForkJoinPool
 * 将一个大任务分成多个小任务执行，相当于归并排序，先分，最后将结果汇总
 * 它必须传入ForkJoinTask类型任务，而ForkJoinTask实现起来比较麻烦，我们可以使用它的两个子类RecursiveAction、RecursiveTask
 */
public class Demo12_ForkJoinPool {
	static int[] nums = new int[1000000];
	static final int MAX_NUM = 50000;
	static Random r = new Random();

	static {
		for(int i=0; i<nums.length; i++) {
			nums[i] = r.nextInt(100);
		}
		System.out.println(Arrays.stream(nums).sum());
	}

	public static void main(String[] args) {
		ForkJoinPool forkJoinPool = new ForkJoinPool();
		AddTask task = new AddTask(0, nums.length);
		forkJoinPool.execute(task);
		long result = task.join();
		System.out.println(result);

		try {
			TimeUnit.SECONDS.sleep(10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	//这种只fork不join，就是说只执行任务，但是不返回结果
	/*static class AddTask extends RecursiveAction {
		
		int start, end;
		
		AddTask(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected void compute() {
			if(end-start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++){
					sum += nums[i];
				}
				System.out.println(sum);
			} else {
				int middle = start + (end-start)/2;
				AddTask subTask1 = new AddTask(start, middle);
				AddTask subTask2 = new AddTask(middle, end);
				subTask1.fork();
				subTask2.fork();
			}
		}
	}*/

	//有fork有join，就是说会把结果汇总并返回
	static class AddTask extends RecursiveTask<Long> {

		int start, end;
		
		AddTask(int s, int e) {
			start = s;
			end = e;
		}

		@Override
		protected Long compute() {
			if(end-start <= MAX_NUM) {
				long sum = 0L;
				for(int i=start; i<end; i++){
					sum += nums[i];
				}
				return sum;
			} 
			int middle = start + (end-start)/2;
			AddTask subTask1 = new AddTask(start, middle);
			AddTask subTask2 = new AddTask(middle, end);
			subTask1.fork();
			subTask2.fork();
			return subTask1.join() + subTask2.join();
		}
	}
	


}

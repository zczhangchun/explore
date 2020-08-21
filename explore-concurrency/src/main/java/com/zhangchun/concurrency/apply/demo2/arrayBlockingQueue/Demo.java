package com.zhangchun.concurrency.apply.demo2.arrayBlockingQueue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * ArrayBlockingQueue
 *
 * 有界队列，意思就是说这个队列能装的元素的个数是固定的，后面讲线程池的时候，里面装的其实是一个个任务。
 * 这里只能装10个，如果超过了可能会出问题可能会阻塞，这里看你调用什么方法。
 * add会报异常
 * offer不会报异常，他只通过布尔类型的返回值来告诉你是加成功了还是没有加成功。
 * offer可以设置时间,如果这段时间加不进去就不加了也就是返回false
 * put方法是满了会阻塞住。
 */
public class Demo {

	private static BlockingQueue<String> strings = new ArrayBlockingQueue<>(10);
	
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 10; i++) {
			strings.put("a" + i);
		}
		strings.add("aaaa");
//		strings.put("aaaa");
//		strings.offer("aaaa");
		strings.offer("aaaa",1, TimeUnit.SECONDS);
		System.out.println(strings);
	}
}

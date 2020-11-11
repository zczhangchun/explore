package com.monkey.concurrency.apply.demo2.synchronizedQueue;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

/**
 * SynchronizedQueue 同步队列
 * 同步队列是容量为0，也就是来的东西必须给消费掉.
 * 首先启动一个消费者，调用add方法，他报错了。
 * 原因：这个队列容量是0，可以理解成是这个队列一开始就是满的，他是有界队列，只不过界限是0，所以添加的时候（add方法）发现队列是满的就会报错。
 * 只能调用put，意思就是阻塞等待消费者消费。put里面其实用的是transfer，任何东西必须消费，不能往容器里面扔。
 * 原因：put本身不放入队列中，所以就不会报错。put的时候会阻塞，等待消费者来拿（等于一手交钱一手交货，不由队列来做第三方）
 * 总结：这个队列不像别的队列，别的队列会拿来装东西，这个队列不装东西，而是直接将生产的数据丢给消费者。
 *
 */
public class Demo {

	public static void main(String[] args) throws InterruptedException {
		BlockingQueue<String> strings = new SynchronousQueue<>();
		
		new Thread(()->{
			try {
				System.out.println(strings.take());
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
//		strings.add("aaa");
		strings.put("aaa");
		strings.put("aaa");
		strings.put("aaa");
		strings.put("aaa");
		strings.put("aaa");
		System.out.println(strings.size());
	}
}

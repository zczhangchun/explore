package com.monkey.concurrency.apply.demo2.concurrentLinkedQueue2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * 有10000张火车票,同时有10个窗口对外售票
 * 请写一个模拟程序
 *
 * 在JDK1.5以后，java里面提供了很多的并发容器，这里我们用的是一个queue，队列。
 * 所谓队列其实就是一个容器，就是站成一对，不管票还是人都在里面排成一堆，队列有几种，有先进先出的，
 * 还有两端的队列，还有就是栈，先进后出，先加进去的后出来。
 * 这里用了一个concurrentLinkedQueue，并发的链表队列。线程里面调用了一个poll方法，
 * 意思是往外面拿一个数据，相当于在尾巴里面拿一个，如果没有拿到，他的返回值就是空，那么就中断线程。
 * 这里面没有加锁，同样有判断，但是不会出问题。完成卖票功能这种效率是比较高的。queue里面是不能装空值。
 * 这里虽然判断和操作是一起的，但是我们没有在判断里面有任何操作，大不了反过头来再拿一边，
 * poll底层实现是cas，这里我们就不用加锁了。
 */
public class SaleOfTickets4 {

	private static Queue<Integer> tickets = new ConcurrentLinkedQueue<>();
	
	static {
		for (int i = 0; i < 10000; i++) {
			tickets.add(i);
		}
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				while(true){
					Integer poll = tickets.poll();
					if(poll == null){
						break;
					}
					System.out.println("销售票编号:" + poll);
				}
			}).start();
		}
	}
}

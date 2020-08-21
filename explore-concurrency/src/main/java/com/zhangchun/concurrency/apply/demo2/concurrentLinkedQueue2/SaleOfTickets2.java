package com.zhangchun.concurrency.apply.demo2.concurrentLinkedQueue2;

import java.util.Vector;
import java.util.concurrent.TimeUnit;

/**
 * 有10000张火车票,同时有10个窗口对外售票
 * 请写一个模拟程序
 */
public class SaleOfTickets2 {

	private static Vector<Integer> tickets = new Vector<>();
	
	static {
		for (int i = 0; i < 10000; i++) {
			tickets.add(i);
		}
	}


	/**
	 * vector是一个同步容器
	 * 这里是判断和操作分离了,虽然说在vector里面remove方法是原子性的，但是他的判断和remove方法是分离的，
	 * 但是可能在判断到remove的过程当中线程可能会被打断。我们可以加一个模拟性的睡眠，因为在你实际开发的
	 * 时候，可能在这中间有些判断代码逻辑代码。
	 * 如果剩了最后一个了，很多线程去抢票，虽然size是原子性的，remove是原子性的，但是在他们的中间，
	 * 线程还是有可能被打断
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				while(tickets.size() > 0){
					try {
						TimeUnit.MILLISECONDS.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					System.out.println("销售票编号:" + tickets.remove(0));
				}
			}).start();
		}
	}
}

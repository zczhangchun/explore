package com.zhangchun.concurrency.apply.demo2.concurrentLinkedQueue2;

import java.util.ArrayList;
import java.util.List;

/**
 * 有10000张火车票,同时有10个窗口对外售票
 * 请写一个模拟程序
 */
public class SaleOfTickets1 {

	private static List<Integer> tickets = new ArrayList<>();
	
	static{
		for (int i = 0; i < 10000; i++) {
			tickets.add(i);
		}
	}

	/**
	 * 当我们卖到最后一张票的时候，tickets是大于0的，第一个线程看到他大于0，进入判断，
	 * 第二个线程同样操作区remove的时候票就没有了，在高并发的情况下，还可能会卖重,
	 * 几百万线程去卖票的时候，remove方法不是同步的，第一个线程卖了这张票，第二个线程可能也买了。
	 * @param args
	 */
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(()->{
				while(tickets.size() > 0){
					System.out.println(Thread.currentThread().getName()+"销售票编号:" + tickets.remove(0));
				}
			}).start();
		}
	}
}

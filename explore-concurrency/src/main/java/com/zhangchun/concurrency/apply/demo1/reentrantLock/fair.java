package com.zhangchun.concurrency.apply.demo1.reentrantLock;

import java.util.concurrent.locks.ReentrantLock;

public class fair extends Thread{
	//ReentrantLock可以指定是否为公平锁，true为公平，默认为false
	//按照排队的时间来拿锁，谁等的时间多谁就拿到锁，
	private static ReentrantLock lock = new ReentrantLock(true);

	@Override
	public void run() {

		for (int i = 0; i < 100 ; i++) {
			lock.lock();
			try {
				System.out.println(Thread.currentThread().getName() + "获得锁");
				Thread.sleep(3000);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}
	}
	
	public static void main(String[] args)throws Exception {
		fair fair = new fair();
		Thread t1 = new Thread(fair);
		Thread t2 = new Thread(fair);
		t1.start();
		Thread.sleep(500);
		t2.start();
	}

}

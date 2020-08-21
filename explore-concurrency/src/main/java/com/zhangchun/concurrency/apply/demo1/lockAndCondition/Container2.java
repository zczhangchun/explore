package com.zhangchun.concurrency.apply.demo1.lockAndCondition;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 使用Lock和Condition来实现
 * condition就是在什么条件下怎么做
 * 对比上一个例子，Condition的方式可以更加精确的指定哪些线程被唤醒
 * 上面例子有时候有两个生产者，那么有可能一个生产者唤醒全部的时候只想唤醒消费者，但是把另一个生产者也唤醒起来了，这不是我们想要的
 * Condition帮我们解决了问题，让我们可以指定要唤醒消费者还是生产者。
 */
public class Container2<T> {

	private final LinkedList<T> lists = new LinkedList<>();
	private final int MAX = 10;
	private int count = 0;
	
	private Lock lock = new ReentrantLock();
	private Condition producer = lock.newCondition();
	private Condition consumer = lock.newCondition();
	
	public void put(T t){
		try {
			lock.lock();
			while (lists.size() == MAX) {
				producer.await();
			}
			lists.add(t);
			++count;
			//只唤醒消费者线程
			consumer.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
	}
	
	public T get(){
		T t = null;
		try {
			lock.lock();
			while (lists.size() == 0) {
				consumer.await();
			}
			
			t = lists.removeFirst();
			count --;
			//只唤醒生产者线程
			producer.signalAll();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			lock.unlock();
		}
		
		return t;
	}
	
	public static void main(String[] args) {
		Container2<String> c = new Container2<>();
		for (int i = 0; i < 100; i++) {
			new Thread(()->{
				for (int j = 0; j < 5; j++) {
					System.out.println(c.get());
				}
			}, "c" + i).start();
		}
		
		try {
			TimeUnit.SECONDS.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		for (int i = 0; i < 2; i++) {
			new Thread(()->{
				for (int j = 0; j < 25; j++) {
					c.put(Thread.currentThread().getName() + " " + j);
				}
			}, "p" + i).start();
		}
	}
}

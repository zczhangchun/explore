package com.zhangchun.concurrency.apply.demo3;

import java.util.concurrent.Executor;

/**
 * 认识Executor
 * 它是一个线程池，里面只有一个执行的方法
 * JDK8在线文档地址:https://docs.oracle.com/javase/8/docs/api/index.html
 */
public class Demo1_Executor implements Executor{

	public static void main(String[] args) {

	}

	@Override
	public void execute(Runnable command) {
		command.run();
	}
}

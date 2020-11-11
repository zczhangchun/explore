package com.monkey.concurrency.apply.demo2.collectionsSyncheonized;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Collections
 *
 * collections是java里面一个集合处理类，里面有给容器加锁的方法，通过调用api可以返回一个加了锁的容器。
 */
public class Demo {

	public static void main(String[] args) {
		ArrayList<String> arrayList = new ArrayList<>();
		List<String> synchronizedList = Collections.synchronizedList(arrayList);
	}
}

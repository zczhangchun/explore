package com.monkey.spring.source.aop;

/**
 * 计算类接口
 * Created by smlz on 2019/6/10.
 */
public interface Calculate {

    /**
     * 加法
     * @param numA
     * @param numB
     * @return
     */
     int add(int numA, int numB);
    int mod(int numA, int numB);
}

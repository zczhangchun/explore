package com.zhangchun.spring.source.aop;

/**
 * Created by smlz on 2019/6/10.
 */
public class MyCalculate implements Calculate {
    public int add(int numA, int numB) {
        System.out.println("执行目标方法:add");
        return numA+numB;
    }
    public int mod(int numA,int numB){
        System.out.println("执行目标方法:mod");
//        int retVal = ((Calculate) AopContext.currentProxy()).add(numA,numB);
        int retVal = this.add(numA,numB);
        return retVal%numA;
    }
}

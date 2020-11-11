package com.monkey.spring.annotation.beanLifecycle;

/**
 * @author zhangchun
 */
public class Book {

   public void init(){
       System.out.println("出生");
   }

   public void destory(){
       System.out.println("销毁");
   }
}

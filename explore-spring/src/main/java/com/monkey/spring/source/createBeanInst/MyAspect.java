package com.monkey.spring.source.createBeanInst;


/**
 * Created by smlz on 2019/6/3.
 */
public class MyAspect {

    private MyLog myLog;



    public MyLog getMyLog() {
        return myLog;
    }

    public void setMyLog(MyLog myLog) {
        this.myLog = myLog;
    }

    @Override
    public String toString() {
        return "MyAspect{" +
                "myLog=" + myLog +
                '}';
    }
}

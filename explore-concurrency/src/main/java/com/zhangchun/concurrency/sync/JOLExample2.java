package com.zhangchun.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;

import static java.lang.System.out;

/**
 * 打印计算了hashCode的对象头信息
 */
public class JOLExample2 {
    public static void main(String[] args) throws Exception {
        A a= new A();
        out.println("befor hash");
        //没有计算HASHCODE之前的对象头
        out.println(ClassLayout.parseInstance(a).toPrintable());
        //JVM 计算的hashcode
        out.println("jvm------------16进制："+Integer.toHexString(a.hashCode()));
//        HashUtil.countHash(a);
        //当计算完hashcode之后，我们可以查看对象头的信息变化
        out.println("after hash");
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

package com.zhangchun.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

import static java.lang.System.out;

/**
 * 打印对象头的信息
 */
public class JOLExample1 {
    static  A a = new A();
    public static void main(String[] args) {
        //jvm的信息
        out.println(VM.current().details());
        out.println(ClassLayout.parseInstance(a).toPrintable());
    }
}

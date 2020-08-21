package com.zhangchun.concurrency.sync;
import org.openjdk.jol.info.ClassLayout;

import java.util.ArrayList;
import java.util.List;

import static java.lang.System.out;

/**
 * 批量重偏向证明
 *
 * 我们在JOLExample12证明了偏向锁不会"单个"重偏向，
 * 这里我们要证明偏向锁是会批量偏向锁是会"批量"重偏向的，但是是有一定条件的。
 * 需要使用参数将偏向锁延迟关闭
 * -XX:+UseBiasedLocking -XX:BiasedLockingStartupDelay=0
 *
 * 解释：会"批量"重偏向是有一定条件的，
 * 下面代码分析：就是一个Class创建出多个实例，总共有30个实例，这些实例拿来当锁。
 * t1线程创建了30个实例对象，并使用这些实例对象来当锁，然后放进集合中。 此时这些实例对象都是偏向锁
 * 执行完成后，t2再去拿t1用的这些实例对象锁，因为都是偏向锁，偏向锁不会重偏向，所以都升级成轻量锁
 * 但是！！t2调用的是A的class的实例对象。JVM就会发现，这个A的class所创建出来的实例怎么老是从偏向锁升级成轻量锁，老升级会消耗我JVM的性能
 * 消耗性能的原因在于：需要撤销偏向锁，然后升级，这两步很消耗性能
 * 于是，在执行第20个的时候，不再对他们进行升级成轻量锁，而是进行重偏向，而且是把20个之后的对象都进行重偏向把偏向t1线程改为偏向t2线程，这样就能加强性能了。
 * 而且这个重新偏向不是对单个实例对象锁进行改变，而是批量对20个之后的对象全部都修改偏向。
 */
public class JOLExample13 {
    static List<A> list = new ArrayList<A>();
    public static void main(String[] args) throws Exception {
        Thread t1 = new Thread(() -> {
            for (int i=0;i<30;i++){
                A a = new A();
                synchronized (a){
                    list.add(a);
                }
            }
            out.println("t1结束");
        });
        t1.start();
        t1.join();
        out.println("befre t2");
        //偏向，如果下面代码不注释，很难看到t2在小于20次的时候打印的是偏向锁
        //原因应该是下面这句执行执行时间太长，所以t2很难刚好占到t1线程的位置（线程ID），就很难出现偏向锁。
        out.println(ClassLayout.parseInstance(list.get(1)).toPrintable());
        Thread t2 = new Thread() {
            int k=0;
            public void run() {
                for(A a:list){
                   synchronized (a){
                       if (k == 21){
                           out.println("t2 ing：" + k );
                           //轻量锁？偏向锁  -> 看次数，超过20次就是轻量锁
                           //这里也有可能小于20次的时候就是偏向锁，那么这种情况，在20次的时候也不会批量偏向，因为前20次就一直都是偏向锁，没有出现偏向锁撤销升级成轻量锁的情况
                           //所以如果前面20次都是偏向锁，那么20次之后也是和前面20次一样的偏向锁（偏向线程ID也还是t1的）
                           //原因：虽然小于20次也是偏向锁，但是仔细观察偏向的线程ID其实还是一样的。因为t1销毁的时候t2刚好占了t1的位置（占了它的线程ID）
                           // 所以sync认为是同一个线程在用锁，就不会升级成轻量锁
                           // （不懂可以看JOLExample12）
//                           out.println(ClassLayout.parseInstance(list.get(1)).toPrintable());
                           for (int i = 18; i < 22; i++) {
                               out.println(i + "i----------------- ");
                               out.println(ClassLayout.parseInstance(list.get(i)).toPrintable());
                           }
                       }
                   }
                    k++;
                }

            }
        };
        t2.start();
    }
}
package create.singleton;

/**
 * @author zhangchun
 * 饿汉式
 * 最简单也实用的单例模式
 * 优点：任何并发问题都没有
 * 缺点：在类被初始化的时候，对象就会被创建，如果我们不想用这个实例，但是他已经被创建了，就浪费内存了
 */
public class Demo1 {
    private static Demo1 demo1 = new Demo1();

    private Demo1(){}

    public static Demo1 getInstance() {
        return demo1;
    }

}

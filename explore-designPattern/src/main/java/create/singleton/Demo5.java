package create.singleton;

/**
 * @author zhangchun
 * 懒汉式四
 * 静态内部类
 * 静态内部类的方式效果类似双检锁，但实现更简单。但这种方式只适用于静态域的情况，双检锁方式可在实例域需要延迟初始化时使用。
 */
public class Demo5 {

    private Demo5(){}

    private static class inner{
         private static final Demo5 instance = new Demo5();
    }

    public static final Demo5 getInstance() {
        return inner.instance;
    }
}

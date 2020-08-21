package create.singleton;

/**
 * @author zhangchun
 * 懒汉式三
 * 双重检索判断
 * 优点：不用它时，则不会先创建实例，就不会浪费内存，线程安全
 * 缺点：两个锁，两个判断，性能不太好
 */
public class Demo4 {
    private volatile static Demo4 demo4 = null;

    private Demo4() {}

    public static Demo4 getInstance() {
        if (demo4 == null){
            synchronized (Demo4.class){
                if (demo4 == null){
                    synchronized (Demo4.class){
                        if (demo4 == null){
                            demo4 = new Demo4();
                        }
                    }

                }
            }
        }
        return demo4;

    }
}

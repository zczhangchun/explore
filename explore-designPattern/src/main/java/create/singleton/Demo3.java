package create.singleton;

/**
 * @author zhangchun
 * 懒汉式二
 * 优点：不用它时，则不会先创建实例，就不会浪费内存
 * 缺点：synchronized放在if里面还是会有线程安全问题
 */
public class Demo3 {
    private static Demo3 demo3 = null;

    private Demo3() { }

    public static Demo3 getInstance() {
        if (demo3 == null){
            synchronized (Demo3.class){
                demo3 = null;
            }
        }
        return demo3;
    }
}

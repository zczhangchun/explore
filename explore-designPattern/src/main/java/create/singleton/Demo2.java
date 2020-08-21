package create.singleton;

/**
 * @author zhangchun
 * 懒汉式一
 * 优点：不用它时，则不会先创建实例，就不会浪费内存
 * 缺点：线程不安全
 */
public class Demo2 {
    private static Demo2 demo2 = null;

    private Demo2(){}

    public static Demo2 getInstance() {
        if (demo2 == null){
            demo2 = new Demo2();
        }
        return demo2;
    }

}

package create.prototype;

/**
 * @author zhangchun
 * 原型模式
 * 其实java已经为我们提供
 * 就是复制一个已经创建了的对象
 */
public class Demo implements Cloneable{
    public static void main(String[] args)throws Exception {
        Demo demo1 = new Demo();
        Object demo2 = demo1.clone();
    }
}

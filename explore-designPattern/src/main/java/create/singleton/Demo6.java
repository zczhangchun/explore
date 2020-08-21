package create.singleton;

/**
 * @author zhangchun
 * 枚举
 * 最完美，但是没什么卵用
 * 支持序列化机制，绝对防止多次实例化
 */
public enum  Demo6 {
    INSTANCE;
    public void anyMethod(){}
}

package create.builder;

/**
 * @author zhangchun
 * 建造者模式
 * 建造者（Builder）模式的主要角色如下。
 * 产品角色（Product）：它是包含多个组成部件的复杂对象，由具体建造者来创建其各个滅部件。
 * 抽象建造者（Builder）：它是一个包含创建产品各个子部件的抽象方法的接口，通常还包含一个返回复杂产品的方法 getResult()。
 * 具体建造者(Concrete Builder）：实现 Builder 接口，完成复杂产品的各个部件的具体创建方法。
 * 指挥者（Director）：它调用建造者对象中的部件构造与装配方法完成复杂对象的创建，在指挥者中不涉及具体产品的信息。
 *
 * 问题：还原@Builder
 */
public class Main {
    public static void main(String[] args) {
        Product build = Product.builder().buildSofa("沙发")
                .buildTV("TV")
                .build();
        System.out.println(build);
    }
}

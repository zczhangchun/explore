package structure.decorator;

/**
 * 分析：在《恶魔战士》中，游戏角色“莫莉卡·安斯兰”的原身是一个可爱少女，
 * 但当她变身时，会变成头顶及背部延伸出蝙蝠状飞翼的女妖，
 * 当然她还可以变为穿着漂亮外衣的少女。这些都可用装饰模式来实现，
 *
 *
 * 在本实例中的“莫莉卡”原身有 setImage(String t) 方法决定其显示方式，
 * 而其 变身“蝙蝠状女妖”和“着装少女”可以用 setChanger() 方法来改变其外观，原身与变身后的效果用 display() 方法来显示
 */
public class Main {
    public static void main(String[] args) {

        //创建原身
        Morrigan m1 = new MorriganOrigin();
        m1.display();
        //创建变身的女妖
        Morrigan m2 = new MorriganSuccubus(m1);
        m2.display();
        //创建变身的女孩
        Morrigan m3 = new MorriganGirl(m1);
        m3.display();

    }
}

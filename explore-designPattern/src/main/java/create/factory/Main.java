package create.factory;

import create.factory.animal.Animal;
import create.factory.factory.AbstractFactory;
import create.factory.factory.OneFactory;
import create.factory.factory.TwoFactory;

/**
 * @author zhangchun
 * 工厂模式
 * 有很多种类的畜牧场，如养牛场用于养牛，养羊场用于养羊，所以该实例用工厂方法模式比较适合。
 * 适用于：一个工厂不需要生产多个同种等级不同类型的产品。例如可以养牛，但是不能养羊。羊和牛是同种类型不同等级
 * 题目：要一个养牛场来养牛，要一个养羊场养羊。
 *
 */
public class Main {
    public static void main(String[] args) {
        //生产牛，就用one工厂
        AbstractFactory factory1 = new OneFactory();
        Animal animal1 = factory1.newAnimal();
        animal1.show();

        //生产羊，就会two工厂
        AbstractFactory factory2 = new TwoFactory();
        Animal animal2 = factory2.newAnimal();
        animal2.show();;
    }
}

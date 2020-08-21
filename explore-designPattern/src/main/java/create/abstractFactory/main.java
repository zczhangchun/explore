package create.abstractFactory;

/**
 * @author zhangchun
 * 抽象工厂
 * 工厂模式的每个工厂只能同等级商品，如畜牧场只养动物、电视机厂只生产电视机、计算机软件学院只培养计算机软件专业的学生等。
 * 抽象工厂，能生产多等级多种类 的产品，如农场里既养动物又种植物，养动物既可以养牛也可以养羊，种植植物既可以种水果也可以种蔬菜
 *
 * 题目：农场中除了像畜牧场一样可以养动物，还可以培养植物，如养马、养牛、种菜、种水果等，所以本实例比前面介绍的畜牧场类复杂，必须用抽象工厂模式来实现。
 * 分析：抽象工厂模式来设计两个农场，一个是One农场用于养牛和种菜，一个是Two农场用于养羊和种水果，可以在以上两个农场中定义一个生成动物的方法 newAnimal() 和一个培养植物的方法 newPlant()。
 */
public class main {
    public static void main(String[] args) {





        ////用One工厂养牛和蔬菜
        //AbstractFactory factory1 = new OneFactory();
        ////牛
        //Animal animal1 = factory1.newAnimal();
        //animal1.show();
        ////蔬菜
        //Botany botany1 = factory1.newBotany();
        //botany1.show();
        //
        ////用Two工厂养羊和水果
        //AbstractFactory factory2 = new TwoFactory();
        ////羊
        //Animal animal2 = factory2.newAnimal();
        //animal2.show();
        ////水果
        //Botany botany2 = factory2.newBotany();
        //botany2.show();


    }
}

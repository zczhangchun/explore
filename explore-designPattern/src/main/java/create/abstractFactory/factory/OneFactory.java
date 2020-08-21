package create.abstractFactory.factory;

import create.abstractFactory.animal.Animal;
import create.abstractFactory.animal.Cow;
import create.abstractFactory.botany.Botany;
import create.abstractFactory.botany.Vegetables;

/**
 * @author zhangchun
 * 这个工厂生产牛，和种植蔬菜
 */
public class OneFactory extends AbstractFactory {


    public Animal newAnimal() {
        return new Cow();
    }

    public Botany newBotany() {
        return new Vegetables();
    }
}

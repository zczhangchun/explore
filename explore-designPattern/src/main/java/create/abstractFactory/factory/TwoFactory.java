package create.abstractFactory.factory;

import create.abstractFactory.animal.Animal;
import create.abstractFactory.animal.Sheep;
import create.abstractFactory.botany.Botany;
import create.abstractFactory.botany.Fruitage;

/**
 * @author zhangchun
 * 这个工厂生产羊，种植水果
 */
public class TwoFactory extends AbstractFactory {

    public Animal newAnimal() {
        return new Sheep();
    }

    public Botany newBotany() {
        return new Fruitage();
    }
}

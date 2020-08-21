package create.factory.factory;

import create.factory.animal.Animal;
import create.factory.animal.Sheep;

/**
 * @author zhangchun
 * 这个工厂生产羊
 */
public class TwoFactory extends AbstractFactory {
    public Animal newAnimal() {
        return new Sheep();
    }
}

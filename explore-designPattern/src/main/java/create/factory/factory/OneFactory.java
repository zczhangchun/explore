package create.factory.factory;

import create.factory.animal.Animal;
import create.factory.animal.Cow;

/**
 * @author zhangchun
 * 这个工厂生产牛
 */
public class OneFactory extends AbstractFactory {
    public Animal newAnimal() {
        return new Cow();
    }
}

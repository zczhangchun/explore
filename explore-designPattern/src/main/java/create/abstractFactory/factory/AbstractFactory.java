package create.abstractFactory.factory;

import create.abstractFactory.animal.Animal;
import create.abstractFactory.botany.Botany;

/**
 * @author zhangchun
 * 这是一个抽象工厂，可以生产动物，可以养植物
 */
public abstract class AbstractFactory {

    public abstract Animal newAnimal();

    public abstract Botany newBotany();

}

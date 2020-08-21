package create.factory.factory;

import create.factory.animal.Animal;

/**
 * @author zhangchun
 * 这是一个抽象工厂，可以生产动物
 */
public abstract class AbstractFactory {

    public abstract Animal newAnimal();
}

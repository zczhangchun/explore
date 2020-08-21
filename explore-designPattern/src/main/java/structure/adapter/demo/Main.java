package structure.adapter.demo;

/**
 * 适配器模式（Adapter）通常适用于以下场景。
 * 以前开发的系统存在满足新系统功能需求的类，但其接口同新系统的接口不一致。
 * 使用第三方提供的组件，但组件接口定义和自己要求的接口定义不同。
 *
 *
 * 新能源汽车的发动机有电能发动机（Electric Motor）和光能发动机（Optical Motor）等，
 * 各种发动机的驱动方法不同，例如，
 * 电能发动机的驱动方法 electricDrive() 是用电能驱动，
 * 而光能发动机的驱动方法 opticalDrive() 是用光能驱动，
 * 它们是适配器模式中被访问的适配者。
 *
 * 客户端希望用统一的发动机驱动方法 drive() 访问这两种发动机，
 * 所以必须定义一个统一的目标接口 Motor，
 * 然后再定义电能适配器（Electric Adapter）和光能适配器（Optical Adapter）去适配这两种发动机。
 */
public class Main {
    public static void main(String[] args) {

        //使用电能驱动适配器驱动电能
        Motor motor1 = new ElectricAdapter();
        motor1.drive();
        //使用光能驱动适配器驱动光能
        Motor motor2 = new OpticalAdapter();
        motor2.drive();

    }
}

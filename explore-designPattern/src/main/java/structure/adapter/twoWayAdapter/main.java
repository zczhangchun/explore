package structure.adapter.twoWayAdapter;

/**
 * 适配器模式（Adapter）可扩展为双向适配器模式，双向适配器类既可以把适配者接口转换成目标接口，也可以把目标接口转换成适配者接口
 */
public class main {
    public static void main(String[] args) {

        TwoWayAdaptee adaptee = new AdapteeRealize();
        //以为调用的目标对象，实际调用的是适配者对象
        TwoWayTarget target = new TwoWayAdapter(adaptee);
        target.request();
        //以为调用的是适配者对象，实际调用的事目标对象
        target = new TargetRealize();
        adaptee = new TwoWayAdapter(target);
        adaptee.specialRequest();

    }
}

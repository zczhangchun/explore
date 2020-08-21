package structure.adapter.twoWayAdapter;

public class TwoWayAdapter implements TwoWayTarget, TwoWayAdaptee{

    private TwoWayAdaptee adaptee;
    private TwoWayTarget target;

    public TwoWayAdapter(TwoWayAdaptee adaptee) {
        this.adaptee = adaptee;
    }

    public TwoWayAdapter(TwoWayTarget target) {
        this.target = target;
    }

    public void specialRequest() {
        //把适配器接口转成目标对象接口
        target.request();
    }

    public void request() {
        //把目标对象接口转成适配器接口
        adaptee.specialRequest();
    }
}

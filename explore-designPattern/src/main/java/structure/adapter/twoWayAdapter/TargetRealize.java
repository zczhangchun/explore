package structure.adapter.twoWayAdapter;

public class TargetRealize implements TwoWayTarget {
    public void request() {
        System.out.println("目标对象被调用");
    }
}

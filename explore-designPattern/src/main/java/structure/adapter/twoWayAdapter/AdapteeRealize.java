package structure.adapter.twoWayAdapter;

public class AdapteeRealize implements TwoWayAdaptee{
    public void specialRequest() {
        System.out.println("适配者对象被调用");
    }
}

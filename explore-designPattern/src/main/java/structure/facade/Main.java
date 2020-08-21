package structure.facade;

/**
 * 门面模式
 * 分析：客户端需要画许多图形，如果客户端在画每个图形都需要创建一个对象时，那么很不统一。
 * 所以可以用一个门面类来包装这些图形对象。客户端只需要创建门面对象，
 * 然后门面对象的不同方法就可以画出各种图形，而不用去关心是怎么画图的。
 */
public class Main {
    public static void main(String[] args) {
        ShapeFacade shapeFacade = new ShapeFacade();

        //画圆形
        shapeFacade.drawCircle();
        //画长放形
        shapeFacade.drawRectangle();
        //画正放形
        shapeFacade.drawSquare();
    }
}

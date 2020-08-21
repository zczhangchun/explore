package structure.strategy;

import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;

/**
 * @author zhangchun
 */
public class Main {
    public static void main(String[] args) {
        Collections.sort(new LinkedList(), new Comparator() {
            public int compare(Object o1, Object o2) {
                return 0;
            }
        });
//        Collections.sort();
//        new Comparator()
    }
}

package specification;

import java.util.ArrayList;
import java.util.List;

/**
 * Usage: <b> this 与 toString() 引起的递归异常 </b>
 *
 * @author lucifer
 *         Date 2016-12-8
 *         Device Aurora R5
 */
public class InfiniteRecursion {
    @Override
    public String toString() {
        //return "InfiniteRecursion{} " + this + "\n";    /* this will call toString() method of this object */
        return "InfiniteRecursion{} " + super.toString() + "\n";
    }

    public static void main(String[] args) {
        List<InfiniteRecursion> list = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            list.add(new InfiniteRecursion());
        System.out.println(list);
    }
}

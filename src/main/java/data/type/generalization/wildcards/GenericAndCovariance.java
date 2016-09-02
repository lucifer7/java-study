package data.type.generalization.wildcards;

import entity.generic.Apple;
import entity.generic.Fruit;
import lombok.extern.log4j.Log4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Usage: <b>泛型的向上转型，使用通配符，确定上边界</b>
 *
 * @author lucifer
 * @date 2016-8-21
 * @device Yoga Pro
 */
public class GenericAndCovariance {
    public static void main(String[] args) {
        // 此处 List 并非能持有任意继承 Fruit的类型
        // 而是 List 引用可以指向继承自Fruit类型的列表
        List<? extends Fruit> flist = new ArrayList<Apple>();
        // Compile error: 找不到合适的方法
        // 什么类型都加不进去
        //flist.add(new Apple());
        //flist.add(new Fruit());
        //flist.add(new Object());

        flist.add(null);    // passed but meaningless
        Fruit f = flist.get(0);     // no doubt it returns a fruit
    }
}

@Log4j
class CompilerIntelligence {
    public static void main(String[] args) {
        List<? extends Fruit> flist = Arrays.asList(new Apple());

        Apple a = (Apple) flist.get(0);
        log.info(a);
        log.info(flist.contains(new Apple()));
        log.info(flist.indexOf(new Apple()));
        log.info(flist.indexOf(a));
        // flist.add(new Apple());

        /* list.add(E e) changes to add(? extends Fruit)，编译器无法判断接受何种类型，无法接受任何类型 */
        /* list.contains(Object o) 不涉及通配符，可以调用 */

        /**
         * 因此，我们编写泛型的时候可以确定哪些方法是**安全的**，可供调用的
         */
    }
}
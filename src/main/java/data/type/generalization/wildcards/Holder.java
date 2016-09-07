package data.type.generalization.wildcards;

import com.google.common.collect.Lists;
import entity.generic.Apple;
import entity.generic.Banana;
import entity.generic.Fruit;
import entity.generic.Fuji;

import java.util.List;

/**
 * Usage: <b>通配符与Object，子类型边界</b>
 * 对于安全调用的方法，应使用 Object 参数
 * 对于严格类型【不允许通配符】的方法，使用 E e 泛型参数
 *
 * @user lucifer
 * @date 2016-9-2
 * @device Aurora R5
 */
public class Holder<T> {
    private T value;
    public Holder() {}
    public Holder(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean equals(Object obj) {
        return value.equals(obj);
    }

    public static void main(String[] args) {
        Holder<Apple> apple = new Holder<>(new Apple());
        Apple d = apple.getValue();
        apple.setValue(d);
        //Holder<Fruit> fruit = apple;        //Compile error: Incompatible type, cannot upcast

        Holder<? extends Fruit> fruit = apple;  // Use upward wildcard
        Fruit f = apple.getValue();
        d = (Apple) fruit.getValue();

        try {
            Banana banana = (Banana) fruit.getValue();      // No Compile error, cast exception
        } catch (Exception e) {
            e.printStackTrace();
        }

        //fruit.setValue(new Apple());        // Compile error cannot call setter
        //fruit.setValue(new Fruit());          // same as former

        System.out.println(fruit.equals(d));        // Output: true

        /* 下边界通配符 */
        List<Apple> apples = Lists.newArrayList();
        SuperTypeWildcards.writeTo(apples);
    }
}

/* 超类型通配符，Apple 是类型参数的下界 */
class SuperTypeWildcards {
    static void writeTo(List<? super Apple> apples) {
        apples.add(new Apple());
        apples.add(new Fuji());
         // apples.add(new Fruit());     // 编译错误，找不到合适的方法
    }
}

/* src is producer 限定上界, dest is consumer 限定下界 */
/* PECS principle, producer extends, consumer super */
class CollectionW<T> {
    void copyTo(List<? extends T> src, List<? super T> dest) {
        for (int i = 0; i < src.size(); i++) {
            dest.set(i, src.get(i));
        }
    }
}
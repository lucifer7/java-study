package data.type.generalization;

import common.entity.generic.Apple;
import common.entity.generic.Fruit;

/**
 * Usage: <b> Overriding method can return different type(sub type) as Super </b>
 *
 * @author Jingyi.Yang
 *         Date 2016/10/19
 **/
public class Covariant {

}

class Base {
    Fruit covariant(Apple apple) {
        return new Fruit();
    }
}

class Sub extends Base {
    @Override
    /* 协变返回类型，即子类返回比父类方法更窄的类型，since Java 5 */
    Apple covariant(Apple apple) {
        return new Apple();
    }
}
/*
class Sub1 extends Base {
    @Override
    Fruit covariant(Fruit apple) {
        return new Apple();
    }
}*/

// 入参逆变，出参协变，貌似Java不支持，将函数看作一种类型
package data.type.generalization;

import entity.generic.Apple;
import entity.generic.Fruit;

/**
 * Usage: <b> </b>
 *
 * @author Jingyi.Yang
 *         Date 2016/10/19
 **/
public class Covariant {

}

class Base {
    Fruit contravriant(Apple apple) {
        return new Fruit();
    }
}

class Sub extends Base {
    @Override
    Apple contravriant(Apple apple) {
        return new Apple();
    }
}
/*
class Sub1 extends Base {
    @Override
    Fruit contravriant(Fruit apple) {
        return new Apple();
    }
}*/

// 入参逆变，出参协变，貌似Java不支持，将函数看作一种类型
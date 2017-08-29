package specification.ch8classes;

import common.entity.generic.Apple;
import common.entity.generic.Fruit;

import java.io.IOException;

/**
 * Usage: <b> An abstract class can override an abstract method by providing another abstract method </b>
 * 1. put doc comment
 * 2. refine the return type
 * 3. declare the set of checked exceptions
 *
 * @author Jingyi.Yang
 *         Date 2017/5/12
 **/
public class AbstractMethodOverridding {
    public static void main(String[] args) {

    }
}

abstract class A {
    abstract Fruit f1() throws IllegalAccessException;
}

abstract class B extends A {
    @Override
    abstract Fruit f1 () throws NumberFormatException;
}

abstract class C extends A {
    @Override
    abstract Apple f1();
}
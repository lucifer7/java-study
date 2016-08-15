package data.type.generalization;

import lombok.extern.log4j.Log4j;

import java.util.ArrayList;

/**
 * Usage: 类型擦除机制，类型参数只存在编译期，运行时不泛型
 * Created by Lucifer
 * Date: 2016-8-15
 * Device: Aurora R5
 */
@Log4j
public class ErasedTypeEquivalence {
    public static void main(String[] args) {
        Class c1 = new ArrayList<String>().getClass();
        Class c2 = new ArrayList<Integer>().getClass();
        log.info(c1 == c2);     // output: true
        log.info(c1);           // class java.util.ArrayList
        //泛型擦除到类型的第一个边界，即ArrayList
        //字节码中加入了类型转换

    }
}

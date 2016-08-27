package data.type.generalization.wildcards;

import entity.generic.Apple;
import entity.generic.Banana;
import entity.generic.Fruit;
import entity.generic.Fuji;
import lombok.extern.log4j.Log4j;

/**
 * Usage: <b>JAVA 中的数组是协变的</b>
 * 子类的数组可以向上转型为父类数组，但元素的实际类型还是子类，放入父类会在运行期报错
 * 但可以放入子类的子类
 *
 * 可以使用泛型，使得错误能在编译期间发现，泛型不支持协变
 *
 * @author lucifer
 * @date 2016-8-27
 * @devide Yoga Pro
 */
@Log4j
public class CovariantArrays {
    public static void main(String[] args) {
        Fruit[] fruit = new Apple[10];
        fruit[0] = new Apple();     // OK
        fruit[1] = new Fuji();      // OK
        log.info(fruit.getClass().getSimpleName());     // Apple[], Runtime type is not Fruit[]

        try {
            fruit[2] = new Fruit();         // Runtime throws ArrayStoreException, compile OK
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            fruit[3] = new Banana();             // Runtime throws ArrayStoreException, compile OK
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

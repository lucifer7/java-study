package java8.ch3lambda;

import java8.factory.AppleFarm;
import java8.factory.Apple;

import java.util.Comparator;
import java.util.List;

/**
 * Usage: <b> Sort apples by Comparator, lambda, method reference </b>
 *
 * @author Jingyi.Yang
 *         Date 2017/4/12
 **/
public class LambdaSorting {
    public static void main(String[] args) {
        // 0. Prepare apples
        List<Apple> inventory = AppleFarm.genList(3);

        // 1. Sort by comparator: weight
        inventory.sort(new AppleComparator());
        System.out.println(inventory);

        // 2. sort by lambda
        inventory.addAll(0, AppleFarm.genList(2));
        inventory.sort((a1, a2) -> a1.getWeight().compareTo(a2.getWeight()));
        System.out.println(inventory);

        // 3. Sort by method reference
        inventory.addAll(0, AppleFarm.genList(2));
        inventory.sort(Comparator.comparing(Apple::getWeight));
        System.out.println(inventory);
    }

    static class AppleComparator implements Comparator<Apple> {
        @Override
        public int compare(Apple o1, Apple o2) {
            return o1.getWeight().compareTo(o2.getWeight());
        }
    }
}

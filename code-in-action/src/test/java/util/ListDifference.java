package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Usage: <b> List 差集与并集计算，待优化 </b>
 *
 * @author Jingyi.Yang
 * @date 2016/9/20
 * Device Dell
 **/
public class ListDifference {
    public static void main(String[] args) {
        /*Collection listOne = new ArrayList(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8));
        Collection listTwo = new ArrayList(Arrays.asList(1, 3, 5, 7));*/

        Collection<String> listOne = new ArrayList(Arrays.asList("milan","dingo", "elpha", "hafil", "meat", "iga", "neeta.peeta"));
        Collection<String> listTwo = new ArrayList(Arrays.asList("hafil", "iga", "binga", "mike", "dingo"));

        //listOne.retainAll(listTwo);         // union set
        //listOne.removeAll(listTwo);         // 左差集
        listTwo.removeAll(listOne);         // 右差集
        System.out.println(listTwo);

        // 遍历三次列表，性能略渣，代码量少

        //System.out.println(listTwo);
    }
}

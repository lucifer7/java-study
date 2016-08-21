package data.type.generalization.Array;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Usage: <b>泛型与数组</b>
 * 直接创建泛型的数组会报错，可以使用集合类代替
 * 真正的泛型数组的创建参考 {@link GenericArray}
 *
 * 数组特性：
 * 1. 大小固定，效率很高
 * 2. 追踪类型，编译期检查插入的类型
 * 3. 可以持有原始类型（容器类也能，有自动装箱）
 * @author lucifer
 * @date 2016-8-19
 * @devide Yoga Pro
 */
public class GenericList {
    public static final int SIZE = 10;

    public static void main(String[] args) {
        //Generic<Integer>[] generic = new Generic<Integer>()[SIZE];        // Error: 需要数组, 但找到data.type.generalization.Array.Generic<java.lang.Integer>
        List<Generic<Integer>> generics = Lists.newArrayList();             // Compile success, use Collection of generic instead of array
    }
}

class Generic<T> {}



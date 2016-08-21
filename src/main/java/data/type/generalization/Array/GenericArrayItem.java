package data.type.generalization.Array;

/**
 * Usage: <b>数组元素是泛型参数</b>
 *
 * @author lucifer
 * @date 2016-8-20
 * @devide Yoga Pro
 */
public class GenericArrayItem<T> {
    private T[] array;

    public GenericArrayItem(int size) {
        this.array = (T[]) new Object[size];        // 创建泛型数组
    }

    public void put(int index, T item) {
        array[index] = item;
    }

    public T get(int index) {
        return array[index];
    }

    public T[] rep() {
        return array;
    }

    public static void main(String[] args) {
        GenericArrayItem<Integer> gai = new GenericArrayItem<Integer>(10);
        //Integer[] ia = gai.rep();           // [Ljava.lang.Object; cannot be cast to [Ljava.lang.Integer;  因为Object[] 无法转型为 Integer[]
        gai.put(0, new Integer(10));
        Object[] oa = gai.rep();
        System.out.println(oa[0]);
    }
}

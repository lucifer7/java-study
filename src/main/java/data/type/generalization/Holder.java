package data.type.generalization;


import lombok.extern.log4j.Log4j;

/**
 * 模拟泛型的包装类，可以是任意类型
 * Created by lucifer on 2016-8-12.
 */
@Log4j
public class Holder {
    private Object a;

    public Holder(Object a) {
        this.a = a;
    }

    public Object getA() {
        return a;
    }

    public void setA(Object a) {
        this.a = a;
    }

    public static void main(String[] args) {
        Holder holder = new Holder("fake generic");
        String s = (String) holder.getA();
        holder.setA(1);
        Integer x = (Integer) holder.getA();

        holder.setA("error convert");
        Integer w = (Integer) holder.getA();    // ClassCastException, occurred only in runtime
    }
}

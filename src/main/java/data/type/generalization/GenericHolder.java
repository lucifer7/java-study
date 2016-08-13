package data.type.generalization;

import lombok.extern.log4j.Log4j;

/**
 * 泛型版本的Holder
 * Created by lucifer on 2016-8-12.
 */
@Log4j
public class GenericHolder<T> {
    private T a;

    public GenericHolder(T a) {
        this.a = a;
    }

    public T getA() {
        return a;
    }

    public void setA(T a) {
        this.a = a;
    }

    public static void main(String[] args) {
        GenericHolder<String> holder = new GenericHolder<>("generic");
        log.info(holder.getA());

        holder.setA("string");
        //holder.setA(1);   // error occurred in compilation
    }
}

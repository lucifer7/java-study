package util.proxy;

/**
 * Usage: <b> Service to be proxied </b>
 * Mind this doesn't need to implement any interface using CGLIB
 *
 * @author Jingyi.Yang
 *         Date 2018/3/6
 **/
public class ServiceA {
    public String toUpper(String str) {
        return str.toUpperCase();
    }

    public Integer length(String str) {
        return str.length();
    }
}

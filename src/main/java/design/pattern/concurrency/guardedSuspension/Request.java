package design.pattern.concurrency.guardedSuspension;

/**
 * Usage: <b>POJO对象，封装请求内容</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @devide Yoga Pro
 */
public class Request {
    private String name;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}

package design.pattern.concurrency.guardedSuspension;

import design.pattern.concurrency.future.Data;

/**
 * Usage: <b>POJO对象，封装请求内容</b>
 *
 * @author lucifer
 * @date 2016-8-27
 * @device Yoga Pro
 */
public class Request {
    private String name;
    private Data response;

    public Request(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Data getResponse() {
        return response;
    }

    public void setResponse(Data response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return "Request{" +
                "name='" + name + '\'' +
                '}';
    }
}

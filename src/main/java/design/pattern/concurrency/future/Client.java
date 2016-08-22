package design.pattern.concurrency.future;

import java.util.Date;

/**
 * Usage: <b>响应请求的客户端，返回封装后的Data</b>
 *
 * @author lucifer
 * @date 2016-8-22
 * @devide Yoga Pro
 */
public class Client {
    public Data request(final String query) {
        final FutureData futureData = new FutureData();
        new Thread() {
            public void run() {
                RealData realData = new RealData(query);
                futureData.setRealData(realData);
            }
        }.start();

        return futureData;
    }
}

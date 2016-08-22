package design.pattern.concurrency.future;

import lombok.extern.log4j.Log4j;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 * @date 2016-8-22
 * @devide Yoga Pro
 */
@Log4j
public class InvokerMain {
    public static void main(String[] args) {
        Client client = new Client();
        log.info("Request begin...");
        Data data = client.request("1235");
        log.info("Request end...");

        try {
            log.info("---Handle other tasks---");
            Thread.sleep(20);           // OK if this is longer than process, exception when shorter
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("=== Final Result is : " + data.getResult());
    }
}

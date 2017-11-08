package network.server.redirector;

/**
 * Usage: <b> Starter of Redirector server </b>
 *
 * @author jingyi
 *         Date 17-11-8
 *         Device Yoga 3 Pro
 */
public class Main {
    public static void main(String[] args) {
        int port = 8000;
        String newSite = "www.baidu.com";

        Redirector redirector = new Redirector(port, newSite);
        redirector.start();
    }
}

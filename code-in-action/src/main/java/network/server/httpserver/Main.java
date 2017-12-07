package network.server.httpserver;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-12-7
 *         Device Aurora R5
 */
@Slf4j
public class Main {
    public static void main(String[] args) {
        try {
            JHttp server = new JHttp(new File("/tmp/"), 8080);
            server.start();
        } catch (IOException e) {
            log.error("Failed to start JHttp server", e);
        }
    }
}

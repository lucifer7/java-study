package network.server;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * Usage: <b> Scan port of local server from 1 to 65535, list used port </b>
 * Try to bind port to server socket, if failed, then it's in use
 *
 * @author lucifer
 *         Date 2017-9-9
 *         Device Aurora R5
 */
@Slf4j
public class LocalPortScan {
    public static void main(String[] args) {
        for (int i = 1; i < 65535; i++) {
            try {
                ServerSocket server = new ServerSocket(i);
            } catch (IOException e) {
                log.error("Port {} already in use, cause {}", i, e);
            }
        }
    }
}

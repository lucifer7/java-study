package network.server.daytime;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Usage: <b> Write date as response and log requests </b>
 * Test by: telnet IP Port
 *
 * @author lucifer
 *         Date 2017-9-9
 *         Device Aurora R5
 */
@Slf4j
public class LoggingDaytimeServer {
    private static final int PORT = 13;

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(50);
        try (ServerSocket server = new ServerSocket(PORT)) {
            while (true) {
                try {
                    Socket connection = server.accept();
                    Callable<Void> task = new DaytimeTask(connection);
                    pool.submit(task);
                } catch (IOException e) {
                    log.error("Accept request error", e);
                } catch (RuntimeException e) {
                    log.error("Unexpected error", e);
                }
            }
        } catch (IOException e) {
            log.error("Could not start server", e);
        } catch (RuntimeException e) {
            log.error("Could not start server", e);
        }
    }

    private static class DaytimeTask implements Callable<Void> {
        private Socket connection;

        public DaytimeTask(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() throws Exception {
            try {
                Date now = new Date();
                log.info("Time {} {}", now, connection.getRemoteSocketAddress());
                Writer out = new OutputStreamWriter(connection.getOutputStream());
                out.write(now.toString() + "\r\n");
                out.flush();
            } catch (IOException e) {
                log.error("Client interrupt connection", e);
            } finally {
                try {
                    connection.close();
                } catch (IOException e) {
                    log.error("Close error", e);    // Should ignore this in product env
                }
            }

            return null;
        }
    }
}

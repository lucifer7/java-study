package network.server.httpserver;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Usage: <b> A server provide file by specified relative path </b>
 * Do not test with browser, use <b>echo 'GET test.txt HTTP/1.0' | nc IP PORT</b>
 *
 * @author lucifer
 *         Date 2017-12-4
 *         Device Aurora R5
 */
@Slf4j
public class JHttp {
    private static final int NUM_THREADS = 50;
    private static final String INDEX_FILE = "index.html";

    private final File rootDir;
    private final int port;

    public JHttp(File rootDir, int port) throws IOException {
        if (!rootDir.isDirectory()) {
            throw new IOException(rootDir + " is not a valid directory");
        }
        this.rootDir = rootDir;
        this.port = port;
    }

    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(NUM_THREADS);

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            log.info("Listening on port {}, root path {}", port, rootDir);

            while (true) {
                Socket socket = serverSocket.accept();
                pool.submit(new RequestProcessor(rootDir, INDEX_FILE, socket));
            }
        } catch (IOException e) {
            log.error("Failed to accept connection", e);
        }
    }
}

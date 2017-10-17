package network.server.singlefile;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Usage: <b> A server that response a single file when receiving any data </b>
 *
 * @author lucifer
 *         <p>
 *         Date 2017-9-9
 *         Device Aurora R5
 */
@Slf4j
public class SingleFileHttpServer {
    private final byte[] content;
    private final byte[] header;
    private final int port;
    private final String encoding;

    public SingleFileHttpServer(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, mimeType, port);
    }

    public SingleFileHttpServer(byte[] content, String encoding, String mimeType, int port) {
        this.content = content;
        this.port = port;
        this.encoding = encoding;

        String header = "HTTP/1.0 200 OK\r\n" +
                "Server: OneFile 2.0\r\n" +
                "Content-length: " + this.content.length + "\r\n" +
                "Content-type: " + mimeType + "; charset=" + encoding + "\r\n";
        this.header = header.getBytes(Charset.forName("US-ASCII"));
    }

    public void start() {
        ExecutorService pool = Executors.newFixedThreadPool(100);
        try (ServerSocket server = new ServerSocket(this.port)) {
            log.info("Accepting connections on port {}", server.getLocalPort());
            log.info("Data to be sent: {}", new String(this.content, encoding));

            while (true) {
                Socket connection = server.accept();
                pool.submit(new HTTPHandler(connection));
            }

        } catch (IOException e) {
            log.error("Start server error");
        }
    }

    private class HTTPHandler implements Callable<Void> {
        private final Socket connection;

        public HTTPHandler(Socket connection) {
            this.connection = connection;
        }

        @Override
        public Void call() throws Exception {
            log.info("Visited by {}", connection.getRemoteSocketAddress());
            try {
                OutputStream out = new BufferedOutputStream(connection.getOutputStream());
                InputStream in = new BufferedInputStream(connection.getInputStream());

                StringBuilder request = new StringBuilder(80);
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) break;   //Only the first line
                    request.append((char) c);
                }

                // For HTTp/1.0 or higher version, add a MIME header
                if (request.toString().indexOf("HTTP/") != -1) {
                    out.write(header);
                }
                out.write(content);
                out.flush();
            } catch (IOException e) {
                log.error("Error writing to client", e);
            } finally {
                connection.close();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        int port = 80;
        String encoding = "UTF-8";

        try {
            Path path = Paths.get("C:\\Users\\skyvo\\Desktop\\test.txt");
            byte[] data = Files.readAllBytes(path);

            String contentType = URLConnection.getFileNameMap().getContentTypeFor(".txt");
            SingleFileHttpServer server = new SingleFileHttpServer(data, encoding, contentType, port);
            server.start();
        } catch (IOException e) {
            log.error("Failed to run file server", e);
        }
    }
}

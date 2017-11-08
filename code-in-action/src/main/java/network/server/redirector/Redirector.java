package network.server.redirector;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.CharsetEncoder;
import java.util.Date;

@Slf4j
public class Redirector {
    private final int port;
    private final String newSite;

    public Redirector(int port, String newSite) {
        this.port = port;
        this.newSite = newSite;
    }

    public void start() {
        try (ServerSocket server = new ServerSocket(port)) {
            log.info("Redirecting server start listening on port {} to {}", port, newSite);

            while (true) {
                try {
                    Socket s = server.accept();
                    new RedirectThread(s).start();
                } catch (IOException e) {
                    log.error("Exception while accepting connection", e);
                } catch (RuntimeException e) {
                    log.error("Unexpected error", e);
                }
            }
        } catch (BindException e) {
            log.error("Could not bind server to port {}", port, e);
        } catch (IOException e) {
            log.error("Failed to open server socket", e);
        }
    }

    private class RedirectThread extends Thread {
        private final Socket connection;


        private RedirectThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            try {
                Writer out = new BufferedWriter(
                        new OutputStreamWriter(connection.getOutputStream(), "US-ASCII"));
                Reader in = new InputStreamReader(new BufferedInputStream(connection.getInputStream()));

                StringBuilder request = new StringBuilder(80);
                while (true) {
                    int c = in.read();
                    if (c == '\r' || c == '\n' || c == -1) break;
                    request.append((char) c);
                }

                // Only read the first line
                String get = request.toString();
                String[] pieces = get.split("\\s+");
                String file = pieces[1];

                // if version newer than HTTP/1.0, send a MIME header
                if (get.indexOf("HTTP") != -1) {
                    out.write("HTTP/1.0 302 FOUND\r\n");
                    out.write("Date: " + new Date() + "\r\n");
                    out.write("Server: Redirector 1.1\r\n");
                    out.write("Location: " + newSite + file + "\r\n");
                    out.write("Content-type: text/html\r\n\r\n");
                    out.flush();
                }

                // If browser does not support redirect, provide warning and new url
                out.write("<html><head><title>Document moved</title></head>\r\n");
                out.write("<body><h1>Document moved</h1>\r\n");
                out.write("The document " + file + " has moved to \r\n");
                out.write("<a href=\"" + newSite + file + "\">" + newSite + file + "</a>.\r\n");
                out.write("</body></html>\r\n");
                out.flush();

                log.info("Redirected {}", connection.getRemoteSocketAddress());
            } catch (IOException e) {
                log.warn("Failed to respond {}", connection.getRemoteSocketAddress(), e);
            } finally {
                try {
                    connection.close();
                } catch (IOException ignore) {
                }
            }
        }
    }
}

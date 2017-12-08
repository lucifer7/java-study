package network.server.httpserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.file.Files;
import java.util.Date;

/**
 * Usage: <b> </b>
 *
 * @author lucifer
 *         Date 2017-12-4
 *         Device Aurora R5
 */
@Slf4j
public class RequestProcessor implements Runnable {
    private File rootDir;
    private String indexFile = "index.html";
    private Socket connection;

    public RequestProcessor(File rootDir, String indexFile, Socket connection) {
        if (!rootDir.isDirectory()) {
            throw new IllegalArgumentException(rootDir + " is not a valid directory");
        }

        try {
            rootDir = rootDir.getCanonicalFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.rootDir = rootDir;
        if (null != indexFile) {
            this.indexFile = indexFile;
        }
        this.connection = connection;
    }

    @Override
    public void run() {
        String root = rootDir.getPath();
        try (OutputStream raw = new BufferedOutputStream(connection.getOutputStream());
             PrintWriter writer = new PrintWriter(raw);
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))
        ) {
            StringBuilder request = new StringBuilder();
            String line;
            while (null != (line = reader.readLine())) {
                request.append(line);
                //break;
            }

            String req = request.toString();
            log.info("Client {} request {}", connection.getRemoteSocketAddress(), req);

            if (StringUtils.isNotBlank(req)) {
                // respond and return
            }

            String[] tokens = req.split("\\s+");
            if ("GET".equalsIgnoreCase(tokens[0]) && tokens.length > 1) {
                String fileName = tokens[1];
                File file = new File(root, fileName);
                if (!file.exists() || !file.canRead() || file.isDirectory()) {
                    String html = buildHtmlBody("404 NOT FOUND", "HTTP Error 404 NOT FOUND");
                    sendHttpHeader(writer, "404 NOT FOUND", "text/html", html.length());
                    writer.write(html);
                    writer.flush();
                } else {
                    String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
                    byte[] data = Files.readAllBytes(file.toPath());
                    if (tokens.length > 2) {
                        String version = tokens[2];
                        if (version.startsWith("HTTP/")) {
                            sendHttpHeader(writer, "200 OK", contentType, data.length);
                        }
                    }
                    byte[] buffer = new byte[2048];
                    InputStream in = new FileInputStream(file);
                    while (in.read(buffer) > 0) {
                        raw.write(buffer);
                    }
                    raw.flush();
                }
            } else {
                String html = buildHtmlBody("501 NOT IMPLEMENTED", "HTTP Error 501 NOT IMPLEMENTED");
                sendHttpHeader(writer, "501 NOT IMPLEMENTED", "text/html", html.length());
                writer.write(html);
                writer.flush();
            }

        } catch (Exception e) {
            log.error("Unexpected error while processing request", e);
        }
    }

    private void sendHttpHeader(PrintWriter writer, String resp, String contentType, int length) throws IOException {
        writer.write("HTTP/1.0 " + resp + "\r\n");
        writer.write("Date: " + new Date() + "\r\n");
        writer.write("Server: JHttp server 1.1\r\n");
        writer.write("Content-type: " + contentType + "\r\n");
        writer.write("Content-length: " + length + "\r\n\r\n");
        writer.flush();
    }

    public String buildHtmlBody(String title, String content) {
        StringBuilder body = new StringBuilder();
        body.append("<html><head><title>")
                .append(title)
                .append("</title></head>\r\n");
        body.append("<body><h1>")
                .append(content)
                .append("</h1>\r\n");
        body.append("</body></html>\r\n");
        return body.toString();
    }
}

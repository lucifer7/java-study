package network.server.httpserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.Charset;
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
    private static final String CHARSET = "UTF-8";
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
             OutputStreamWriter writer = new OutputStreamWriter(raw, CHARSET);
             BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), CHARSET))
        ) {
            String req = readRequest(reader);
            if (StringUtils.isBlank(req)) {
                return;
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
                    byte[] data = IOUtils.toByteArray(new FileReader(file), Charset.forName(CHARSET));
                    if (tokens.length > 2) {
                        String version = tokens[2];
                        if (version.startsWith("HTTP/")) {
                            sendHttpHeader(writer, "200 OK", contentType, data.length);
                        }
                    }
                    raw.write(data);
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
        } finally {
            if (null != connection) {
                try {
                    connection.close();
                } catch (IOException e) {
                    log.error("Failed to close connection", e);
                }
            }
        }
    }

    /**
     * Read request string from input
     *
     * @param reader buffered input reader
     * @return request string
     * @throws IOException if failed while reading
     */
    private String readRequest(BufferedReader reader) throws IOException {
        StringBuilder request = new StringBuilder();
        String line;
        while (null != (line = reader.readLine())) {
            request.append(line);
        }

        String req = request.toString();
        log.info("Client {} request {}", connection.getRemoteSocketAddress(), req);
        return req;
    }

    /**
     * Send response http header to writer, with contentType and length specified
     *
     * @param writer      output stream writer
     * @param resp        response
     * @param contentType content type
     * @param length      length of data
     * @throws IOException if failed while writing
     */
    private void sendHttpHeader(OutputStreamWriter writer, String resp, String contentType, int length) throws IOException {
        writer.write("HTTP/1.0 " + resp + "\r\n");
        writer.write("Date: " + new Date() + "\r\n");
        writer.write("Server: JHttp server 1.1\r\n");
        writer.write("Content-type: " + contentType + "\r\n");
        writer.write("Content-length: " + length + "\r\n\r\n");
        writer.flush();
    }

    /**
     * Build Html body by given title and content
     *
     * @param title   page title
     * @param content page content
     * @return html body as string
     */
    private String buildHtmlBody(String title, String content) {
        return "<html><head><title>" +
                title +
                "</title></head>\r\n" +
                "<body><h1>" +
                content +
                "</h1>\r\n" +
                "</body></html>\r\n";
    }
}

package network.server.httpserver;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

import java.io.*;
import java.net.Socket;
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
                break;
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
                    // 404
                    sendHttpResponse(writer, fileName, "404 NOT FOUND");
                } else {
                    // TODO: 2017-12-7 send http header
                    byte[] buffer = new byte[2048];
                    InputStream in = new FileInputStream(file);
                    while (in.read(buffer) > 0) {
                        raw.write(buffer);
                    }
                    raw.flush();
                }
            } else {
                // 501
                sendHttpResponse(writer, "", "501 NOT IMPLEMENTED");
            }

        } catch (Exception e) {
            log.error("Unexpected error while processing request", e);
        }
    }

    private void sendHttpResponse(PrintWriter writer, String fileName, String resp) throws IOException {
        writer.write("HTTP/1.0 " + resp + "\r\n");
        writer.write("Date: " + new Date() + "\r\n");
        writer.write("Server: JHttp server 1.1\r\n");
        writer.write("Location: " + fileName + "\r\n");
        writer.write("Content-type: text/html\r\n\r\n");
        writer.flush();
    }
}

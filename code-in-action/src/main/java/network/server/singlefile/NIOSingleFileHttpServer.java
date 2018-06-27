package network.server.singlefile;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Iterator;

/**
 * Usage: <b> Return file content using NIO channel </b>
 * Test by: <b>echo '' | nc 192.168.1.2 833</b>
 *
 * @author lucifer
 *         Date 2018-6-27
 *         Device Aurora R5
 */
public class NIOSingleFileHttpServer {
    public static final int PORT = 833;
    private ByteBuffer content;

    public NIOSingleFileHttpServer(ByteBuffer data, String encoding, String mimeType) {
        String header = "HTTP/1.0 200 OK\r\n" +
                "Server: NIOSingleFileHttpServer 1.0\r\n" +
                "Content-length: " + data.limit() + "\r\n" +
                "Content-type: " + mimeType + "; charset=" + encoding + "\r\n";

        byte[] headerData = header.getBytes(Charset.forName("US-ASCII"));
        this.content = ByteBuffer.allocate(data.limit() + headerData.length);
        content.put(headerData);
        content.put(data);
        content.flip();
    }

    public void run() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverChannel.socket();
        Selector selector = Selector.open();
        InetSocketAddress local = new InetSocketAddress(PORT);

        serverSocket.bind(local);
        serverChannel.configureBlocking(false);
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
            while(keys.hasNext()) {
                SelectionKey key = keys.next();
                keys.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ);
                } else if (key.isWritable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer data = (ByteBuffer) key.attachment();
                    if (data.hasRemaining()) {
                        channel.write(data);
                    } else {
                        channel.close();
                    }
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    key.interestOps(SelectionKey.OP_WRITE);
                    key.attach(content.duplicate());
                }
            }
        }
    }

    public static void main(String[] args) throws IOException {
        String fileName = "/tmp/456.txt";
        String contentType = URLConnection.getFileNameMap().getContentTypeFor(fileName);
        Path file = FileSystems.getDefault().getPath(fileName);
        byte[] content = Files.readAllBytes(file);
        ByteBuffer data = ByteBuffer.wrap(content);

        NIOSingleFileHttpServer server = new NIOSingleFileHttpServer(data, "UTF-8", contentType);
        server.run();
    }
}

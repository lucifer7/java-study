package network.server.chargen;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Usage: <b> Channel and Buffer, server side </b>
 * 2 channel: server socket channel(Read) and client socket channel(Write)
 *
 * @author lucifer
 *         Date 2018-1-8
 *         Device Aurora R5
 */
@Slf4j
public class ChargenServer {
    private static final int PORT = 19;

    public static void main(String[] args) {
        byte[] rotation = genRotationBytes();

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            InetSocketAddress address = new InetSocketAddress(PORT);

            ServerSocket socket = serverChannel.socket();
            socket.bind(address);   // Bind server to address
            serverChannel.configureBlocking(false);     // Set to non-blocking

            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);   // Bind server channel to Accept selector
        } catch (IOException e) {
            log.error("Create channel failed", e);
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> keySet = selector.selectedKeys();
            Iterator<SelectionKey> iterator = keySet.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                iterator.remove();

                try {
                    if (selectionKey.isAcceptable()) {      // If key's channel ready to accept new Socket connection
                        ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel clientChannel = server.accept();
                        log.info("Accept connection from {}", clientChannel);
                        clientChannel.configureBlocking(false);

                        SelectionKey writeKey = clientChannel.register(selector, SelectionKey.OP_WRITE);

                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        prepareBuffer(rotation, buffer, 0);
                        writeKey.attach(buffer);        // Attach data to key
                    } else if (selectionKey.isWritable()) {
                        SocketChannel clientChannel = (SocketChannel) selectionKey.channel();
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();     // Get data from key's attachment
                        if (!buffer.hasRemaining()) {
                            buffer.rewind();
                            int first = buffer.get();
                            buffer.rewind();

                            int position = first - ' ' + 1;
                            prepareBuffer(rotation, buffer, position);
                        }
                        clientChannel.write(buffer);       // Write data to client channel
                    }
                } catch (IOException e) {
                    log.info("Write to channel failed", e);
                    selectionKey.cancel();
                    try {
                        selectionKey.channel().close();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Generate an byte array from ' ' to '~', total 95
     *
     * @return byte array
     */
    private static byte[] genRotationBytes() {
        byte[] rotation = new byte[95 * 2];
        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }
        return rotation;
    }

    /**
     * Prepare buffer
     *
     * @param rotation byte array
     * @param buffer   buffer
     * @param position offset position
     */
    private static void prepareBuffer(byte[] rotation, ByteBuffer buffer, int position) {
        buffer.put(rotation, position, 72);
        buffer.put((byte) '\r');
        buffer.put((byte) '\n');
        buffer.flip();
    }
}

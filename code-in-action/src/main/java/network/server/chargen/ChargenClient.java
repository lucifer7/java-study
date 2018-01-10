package network.server.chargen;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * Usage: <b> Channel and Buffer, client side </b>
 *
 * @author lucifer
 *         Date 2018-1-8
 *         Device Aurora R5
 */
@Slf4j
public class ChargenClient {
    private static final String SERVER = "localhost";
    private static final int PORT = 19;

    public static void main(String[] args) {
        try {
            SocketAddress address = new InetSocketAddress(SERVER, PORT);
            SocketChannel channel = SocketChannel.open(address);
            //channel.configureBlocking(false);     // If need non-blocking

            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);

            while (channel.read(buffer) != 1) {
                buffer.flip();  // Set position to start, to use data
                out.write(buffer);
                buffer.clear();
            }

        } catch (IOException e) {
            log.error("Generate char failed", e);
        }
    }
}

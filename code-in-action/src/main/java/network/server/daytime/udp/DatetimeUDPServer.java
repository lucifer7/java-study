package network.server.daytime.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.Charset;
import java.util.Date;

/**
 * Usage: <b> Send current date to client </b>
 * content of request is not important
 *
 * @author lucifer
 *         Date 2018-6-28
 *         Device Aurora R5
 */
public class DatetimeUDPServer {
    private static final int PORT = 13;

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(PORT)) {
            while (true) {
                try {
                    DatagramPacket request = new DatagramPacket(new byte[1024], 1024);
                    socket.receive(request);

                    byte[] data = new Date().toString().getBytes(Charset.forName("US-ASCII"));
                    DatagramPacket response = new DatagramPacket(data, data.length, request.getAddress(), request.getPort());
                    socket.send(response);
                    System.out.println("response to " + request.getAddress());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}

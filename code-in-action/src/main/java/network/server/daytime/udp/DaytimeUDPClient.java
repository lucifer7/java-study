package network.server.daytime.udp;

import java.io.IOException;
import java.net.*;

/**
 * Usage: <b> Get response from Datetime server </b>
 * Content of request is not important
 * soTimeout is necessary, UDP is unreliable
 *
 * @author lucifer
 *         Date 2018-6-28
 *         Device Aurora R5
 */
public class DaytimeUDPClient {
    private static final int PORT = 13;
    private static final String HOSTNAME = "localhost";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            socket.setSoTimeout(10 * 1000);
            InetAddress host = InetAddress.getByName(HOSTNAME);
            DatagramPacket request = new DatagramPacket(new byte[1], 1, host, PORT);
            DatagramPacket response = new DatagramPacket(new byte[1024], 1024);

            socket.send(request);
            socket.receive(response);
            String result = new String(response.getData(), 0, response.getLength(), "US-ASCII");
            System.out.println("result = " + result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

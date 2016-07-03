package concurrency.cancelInterrupt.overwriteInterrupt;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

/**
 * Created by lucifer on 2016-7-3.
 */
public class ReaderThread extends Thread {
    private final Socket socket;
    private final InputStream in;
    private static int BUFSIZE = 100;

    public ReaderThread(Socket socket) throws IOException {
        this.socket = socket;
        this.in = socket.getInputStream();
    }

    // 能处理标准中断，也能关闭底层套接字
    public void interrupt() {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            super.interrupt();
        }
    }

    public void run() {
        try {
            byte[] buf = new byte[BUFSIZE];
            while (true) {
                int count = in.read(buf);
                if (count < 0) {
                    break;
                } else if (count > 0){
                    processBuf(buf, count);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();    /* 允许线程退出 */
        }
    }

    private void processBuf(byte[] buf, int count) {
    }
}

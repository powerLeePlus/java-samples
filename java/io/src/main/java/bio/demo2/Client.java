package bio.demo2; /**
 * TCP client端
 *
 * @author lwq
 * @date 2019/8/2 0002
 */

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Date;

/**
 * TCP client端
 * @author lwq
 * @create 2019-08-02 上午 9:09
 */
public class Client {
    public static void main(String[] args) throws IOException, InterruptedException {
        new Thread(() -> {
            try {
                Socket socket = new Socket("localhost", 8888);
                while (true) {
                    OutputStream os = socket.getOutputStream();
                    try {
                        os.write((new Date() + ":" + "hello tcp socket").getBytes());
                        os.flush();
                        //os.close();
                        Thread.sleep(2000);
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        //socket.close();
    }
}

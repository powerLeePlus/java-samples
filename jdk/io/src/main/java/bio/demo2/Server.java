package bio.demo2; /**
 * TCP server端
 *
 * @author lwq
 * @date 2019/8/2 0002
 */

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP server端
 * @author lwq
 * @create 2019-08-02 上午 9:09
 */
public class Server {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(8888);
        new Thread(() -> {
            try {
                Socket socket = serverSocket.accept();
                String ip = socket.getInetAddress().getHostAddress();
                System.out.println("accept from:" + ip);
                new Thread(() -> {
                    try {
                        InputStream is = socket.getInputStream();

                        byte[] bys = new byte[1024];
                        int len;
                        while ((len = is.read(bys)) != -1) {
                            String str = new String(bys, 0, len);
                            System.out.println("accept msg:" + str);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("---------------------------------------");
                    //socket.close();
                }).start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();


    }
}

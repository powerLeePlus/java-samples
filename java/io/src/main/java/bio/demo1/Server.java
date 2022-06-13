package bio.demo1; /**
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
        // 创建服务端
        ServerSocket serverSocket = new ServerSocket(8888);

        //启动监听：监听客户端连接
        Socket socket = serverSocket.accept();
        String ip = socket.getInetAddress().getHostAddress();
        System.out.println("accept from:" + ip);

        //获取输入流
        InputStream is = socket.getInputStream();
        byte[] bys = new byte[1024];
        int len;

        //持续监听输入流，有输入就获取
        while ((len = is.read(bys)) != -1){
            String str = new String(bys, 0, len);
            System.out.println("accept msg:" + str);
            System.out.println("---------------------------------------");
        }
        //关闭资源
        //socket.close();

    }
}

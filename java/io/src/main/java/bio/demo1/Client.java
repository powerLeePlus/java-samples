package bio.demo1; /**
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
        //创建TCP客户端，这一步成功就说明建立连接成功。
        Socket socket = new Socket("localhost", 8888);

        // 循环给服务端发数据
        while (true) {
            //获取输出流
            OutputStream os = socket.getOutputStream();
            System.out.println("send...");
            os.write((new Date() + ":" + "hello tcp socket").getBytes());
            os.flush();
            //os.close();
            Thread.sleep(5000);
        }
        //释放资源，关闭OutputStream就会同时关闭Socket，已验证
        //socket.close();
    }
}

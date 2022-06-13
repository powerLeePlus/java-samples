package lwq.java.study.netty.demo2.client;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * netty client
 * @author lwq
 * @create 2019-08-02 上午 10:59
 */
public class NettyClient {
    public static void main(String[] args) throws InterruptedException {

        Bootstrap bootstrap = new Bootstrap();
        // NIO线程组，对应了我们IO Client.java中main函数起的线程。
        NioEventLoopGroup group = new NioEventLoopGroup();

        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .handler(new MyClientInitializer());

        //发起异步连接操作
        Channel channel = bootstrap.connect("localhost", 8888).channel();
        //等待服务端监听端口关闭
        //channel.closeFuture().sync();

        boolean isSend = true;
        while (isSend) {
            System.out.println("isActive:" + channel.isActive());
            System.out.println("isOpen:" + channel.isOpen());
            System.out.println("isWritable:" + channel.isWritable());
            System.out.println("isRegistered:" + channel.isRegistered());
            System.out.println("send...");
            //通过Channel提供的接口进行IO操作
            channel.writeAndFlush(new Date() + ": hello world!");
            Thread.sleep(5000);
            if(!channel.isActive()){
                isSend = false;
                System.out.println("exit send");

                channel.close();
            }
        }

    }
}

package lwq.java.study.netty.demo1;

import java.util.Date;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;

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
                .handler(new ChannelInitializer<Channel>() {
                    @Override
                    protected void initChannel(Channel ch) {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                });

        //发起异步连接操作
        Channel channel = bootstrap.connect("127.0.0.1", 8888).channel();
        //等待服务端监听端口关闭
        //channel.closeFuture().sync();

        while (true) {
            System.out.println("send...");
            //通过Channel提供的接口进行IO操作
            channel.writeAndFlush(new Date() + ": hello world! \n");
            Thread.sleep(2000);
        }
    }
}

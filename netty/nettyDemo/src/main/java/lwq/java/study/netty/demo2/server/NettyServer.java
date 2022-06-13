package lwq.java.study.netty.demo2.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * netty server
 * @author lwq
 * @create 2019-08-02 上午 10:53
 */
public class NettyServer {
    public static void main(String[] args) {
        //简化服务端启动的一个类
        ServerBootstrap serverBootstrap = new ServerBootstrap();

        //bossGroup是获取连接的，workerGroup是用来处理连接的，这二个线程组都是死循环
        // boos对应，io中的接受新连接线程，主要负责创建新连接
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        // worker对应 io中的负责读取数据的线程，主要用于读取数据以及业务逻辑处理
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ChannelFuture channelFuture = serverBootstrap
                    //group有二个重载方法，一个是接收一个EventLoopGroup类型参数的方法，一个是接收二个EventLoopGroup类型的参数的方法
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel ch) {
                            ch.pipeline().addLast(new StringDecoder());
                            ch.pipeline().addLast(new MyServerInitializer());
                        }
                    })
                    .bind(8888).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}


package lwq.java.study.netty.demo4;

import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

/**
 * 服务端
 * @author lwq
 * @create 2019-08-02 下午 4:44
 */
public class ChatServer {
    public static void main(String[] args) throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try{
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new MyChatInitializer());
            ChannelFuture channelFuture = serverBootstrap.bind(8787).sync();
            System.out.println("server running, port 8787");
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class MyChatInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //分割收到的Bytebu，根据指定的分隔符
        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatServerHandler());
    }
}

class MyChatServerHandler extends SimpleChannelInboundHandler<String>{
    //保留所有与服务器建立连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    /**
     * 服务器端收到任何一个客户端的消息都会触发这个方法
     * 功能：连接的客户端向服务端发送消息，那么其他客户端都收到此消息，自己收到【自己】+消息
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, final String msg) throws Exception {
        final Channel channel = ctx.channel();
        System.out.println("来自客户端【" + channel.remoteAddress() + "】的消息：" + msg + "\n");
        channelGroup.forEach(ch -> {
            if(channel != ch){
                ch.writeAndFlush(channel.remoteAddress() + "发送的消息：" + msg + "\n");
            }else {
                ch.writeAndFlush("【自己】" + msg + "\n");
            }
        });
    }

    //表示服务端与客户端连接建立
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel(); //其实相当于一个connection

        /**
         * 调用cahnnelGroup的writeAndFlush其实就相当于channelGroup中的每一个channel都writeAndFlush
         * 先去广播，再将自己加入到channelGroupz中
         */
        //channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "加入\n");
        channelGroup.add(channel);
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.submit(new ServerMessage(this));
        executorService.shutdown();
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //channelGroup.writeAndFlush("【服务器】 - " + channel.remoteAddress() + "离开\n");

        //验证一下每次客户端断开连接，连接自动地从channelGroup中删除掉
        System.out.println(channelGroup.size());
        //当客户端和服务端断开连接的时候，下面的那段代码netty会自动调用，所以不需要认为的去调用
        //channelGroup.remove(channel);
    }

    //连接处于活动状态
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() +" 下线了");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    //主动发送数据
    public void sendMessage(String msg){
        channelGroup.writeAndFlush(msg + "\n");
        System.out.println("发送数据：" + msg);
    }

    public class ServerMessage implements Runnable{
        private MyChatServerHandler myChatServerHandler;

        public ServerMessage(MyChatServerHandler myChatServerHandler) {
            this.myChatServerHandler = myChatServerHandler;
        }

        @Override
        public void run() {
            Scanner sc = new Scanner(System.in);
            while (sc.hasNextLine()){
                String msg = sc.nextLine();
                myChatServerHandler.sendMessage(msg);
            }
        }
    }
}

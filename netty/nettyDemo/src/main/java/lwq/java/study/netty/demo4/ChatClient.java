package lwq.java.study.netty.demo4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.Delimiters;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

/**
 * 聊天室 clients
 * @author lwq
 * @create 2019-08-02 下午 10:31
 */
public class ChatClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class)
                    .handler(new MyChatClientInitializer());

            Channel channel = bootstrap.connect("localhost", 8686).sync().channel();

            //标准输入
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));

            //利用死循环，不断读取客户端在控制台上的输入内容
            //for (;;){
            String in = bufferedReader.readLine();
            boolean run = true;
            int count = 0;
            while (run){
                channel.writeAndFlush(bufferedReader.readLine() + "\r\n");
                /*channel.writeAndFlush("$E6,E61928006537,24,80,2019-08-14 04:23:43,1,1,1,0,0,0,0,0,0,0,0,0,15.3,0,0,1858,0.0,0.0,0,0,0,0,25,0,0" + "\r\n");
                System.out.println("发送消息: " + count++);
                Thread.sleep(1000);
                if(in.equals("exit")){
                    run = false;
                }*/
            }
        }finally {
            //eventLoopGroup.shutdownGracefully();
        }
    }
}

class MyChatClientInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        pipeline.addLast(new DelimiterBasedFrameDecoder(4096, Delimiters.lineDelimiter()));
        pipeline.addLast(new StringEncoder(CharsetUtil.UTF_8));
        pipeline.addLast(new StringDecoder(CharsetUtil.UTF_8));
        pipeline.addLast(new MyChatClientHandler());
    }
}

class MyChatClientHandler extends SimpleChannelInboundHandler<String>{
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        ctx.flush();
        System.out.println(msg);
        if("AT+C81+E61928006537".equals(msg)){
            ctx.writeAndFlush("$E6,E61928006537,33,24,2017-05-27 09:43:49,C81, 500");
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}

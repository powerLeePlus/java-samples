package lwq.java.study.netty.demo3_http;

import java.net.URI;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * 提供http服务的netty server
 * @author lwq
 * @create 2019-08-02 下午 3:55
 */
public class HttpServer {
    public static void main(String[] args) throws InterruptedException {
        //bossGroup是获取连接的，workerGroup是用来处理连接的，这二个线程组都是死循环
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            //简化服务端启动的一个类
            ServerBootstrap serverBootstrap = new ServerBootstrap();
            //group有两个重载方法，一个是接收一个EventLoopGroup类型参数的方法，一个是接收两个EventLoopGroup类型的参数的方法
            serverBootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class).childHandler(new MyHttpServerInitializer());

            ChannelFuture channelFuture = serverBootstrap.bind(8899).sync();
            channelFuture.channel().closeFuture().sync();
        }finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}

class MyHttpServerInitializer extends ChannelInitializer<SocketChannel>{

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //http协议的编解码使用的是HttpRequestDecoder和HttpResponseEncoder处理器组合
        //HttpRequestDecoder http请求的解码
        //HttpResponseEncoder http请求的编码
        pipeline.addLast("httpServerCodec", new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler", new MyHttpServerHandler());
    }
}

//浏览器的特性会发送一个/favicon.ico请求，获取网站的图标
class MyHttpServerHandler extends SimpleChannelInboundHandler<HttpObject>{

    //channelRead0是读取客户端的请求并且向客户端返回响应的一个方法
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if(msg instanceof HttpRequest){
            HttpRequest httpRequest = (HttpRequest) msg;
            System.out.println("request method：" + httpRequest.method().name());//get方法

            URI uri = new URI(httpRequest.uri());
            //使用浏览器访问localhost:8899会发送两次请求，其中有一次是localhost:8899/favicon.ico，这个url请求访问网站的图标
            if("/favicon.ico".equals(uri.getPath())){
                System.out.println("请求favicon.ico");
                return;
            }

            //向客户端返回的内容
            ByteBuf content = Unpooled.copiedBuffer("hello world", CharsetUtil.UTF_8);
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE, "text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH, content.readableBytes());

            ctx.writeAndFlush(response);

            //其实更合理的close连接应该判断是http1.O还是1.1来进行判断请求超时时间来断开channel连接。
            ctx.channel().close();

        }
    }
}

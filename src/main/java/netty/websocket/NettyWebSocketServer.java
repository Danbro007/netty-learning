package netty.websocket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrameAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.handler.timeout.IdleStateHandler;
import netty.heartbeat.MyHeartBeatHandler;
import netty.heartbeat.NettyHeartBeatServer;

import java.util.concurrent.TimeUnit;

/**
 * @Classname NettyWebSocketServer
 * @Description TODO
 * @Date 2020/7/13 21:43
 * @Author Danrbo
 */
public class NettyWebSocketServer {
    private int port;

    public NettyWebSocketServer() {
        this.port = 7000;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workEventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossEventLoopGroup, workEventLoopGroup).
                channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 因为是基于 Http 协议，添加 Http 编码和解码器。
                        pipeline.addLast(new HttpServerCodec());
                        // 是以块方式写，添加 ChunkedWriteHandler。这个处理器添加异步写入大容量数据流功能。
                        pipeline.addLast(new ChunkedWriteHandler());

                        /**
                         * 1、Http 在传输过程中是分段的，HttpObjectAggregator 可以将多个段聚合。
                         * 2、这就是为什么当浏览器发送大量数据时会发送好几个 Http 请求。
                         */
                        pipeline.addLast(new HttpObjectAggregator(8192));
                        /**
                         * 1、对应 websocket，它的数据是以 Frame(帧)形式传递的
                         * 2、可以看到 WebSocketFrame 下面的6个子类
                         * 3、浏览器请求时 ws://localhost:6666/hello，hello表示要请求的 uri
                         * 4、WebSocketServerProtocolHandler 的核心功能是把 HTTP 协议升级为 ws 协议。
                         * 5、文本和二进制数据帧被传递到管道中的下一个处理器(由你实现)进行处理。
                         */
                        pipeline.addLast(new WebSocketServerProtocolHandler("/hello"));
                        // 自定义的处理器来处理接收的文本和二进制数据帧
                        pipeline.addLast(new MyWebSocketHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.bind(this.port).sync();
            //监听服务器启动事件
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("群聊服务器启动。。。。");
                } else {
                    System.out.println("启动失败！");
                }
            });
            channelFuture.channel().closeFuture().sync();
        } finally {
            bossEventLoopGroup.shutdownGracefully();
            workEventLoopGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        NettyWebSocketServer server = new NettyWebSocketServer();
        server.run();
    }
}

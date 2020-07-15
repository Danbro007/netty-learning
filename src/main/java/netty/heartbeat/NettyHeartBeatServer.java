package netty.heartbeat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @Classname NettyHeartBeatServer
 * @Description TODO
 * @Date 2020/7/13 21:26
 * @Author Danrbo
 */
public class NettyHeartBeatServer {
    private int port;

    public NettyHeartBeatServer() {
        this.port = 6666;
    }

    public void run() throws InterruptedException {
        NioEventLoopGroup bossEventLoopGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workEventLoopGroup = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossEventLoopGroup, workEventLoopGroup).
                channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        // 添加日志处理器 日志级别 INFO
                        pipeline.addLast(new LoggingHandler(LogLevel.INFO));
                        /**
                         * readerIdleTime 是读空闲
                         * writerIdleTime 是写空闲
                         * allIdleTime 是读空闲或者写空闲
                         * 具体的空闲处理交给 IdleStateHandler 的下一个处理器的 userEventTriggered() 方法处理
                         */
                        pipeline.addLast(new IdleStateHandler(3, 5, 7, TimeUnit.SECONDS));
                        pipeline.addLast(new MyHeartBeatServerHandler());
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
        NettyHeartBeatServer server = new NettyHeartBeatServer();
        server.run();
    }
}

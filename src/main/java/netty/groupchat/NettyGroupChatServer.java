package netty.groupchat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Classname NettyGroupChatServer
 * @Description TODO
 * @Date 2020/7/13 15:01
 * @Author Danrbo
 */
public class NettyGroupChatServer {
    private int port;

    public NettyGroupChatServer() {
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
                        // 添加自定义的处理器
                        pipeline.addLast(new NettyGroupChatServerHandler());
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
        NettyGroupChatServer server = new NettyGroupChatServer();
        server.run();
    }
}

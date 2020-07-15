package netty.heartbeat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import netty.http.MyChannelInitializer;
import netty.simple.NioClientHandler;

/**
 * @Classname NettyHeartBeatClient
 * @Description TODO
 * @Date 2020/7/14 14:32
 * @Author Danrbo
 */

public class NettyHeartBeatClient {
    public static void main(String[] args) {
        // 创建 NioEventLoopGroup
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        // 配置客户端
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MyHeartBeatClientHandler());
                    }
                });
        System.out.println("客户端启动。。。");
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6666).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            //关闭线程组
            eventExecutors.shutdownGracefully();
        }
    }
}



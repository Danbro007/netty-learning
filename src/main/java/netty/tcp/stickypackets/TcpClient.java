package netty.tcp.stickypackets;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Classname TcpClient
 * @Description TODO
 * @Date 2020/7/16 16:37
 * @Author Danrbo
 */
public class TcpClient {
    public static void main(String[] args) {
        NioEventLoopGroup eventExecutors = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventExecutors)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new MyClientHandler());
                    }
                });
        System.out.println("客户端启动。。。");
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 6666).sync();
            channelFuture.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            eventExecutors.shutdownGracefully();
        }
    }
}

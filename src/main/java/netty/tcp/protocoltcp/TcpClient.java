package netty.tcp.protocoltcp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Classname TcpClient
 * @Description TODO 解决TCP粘包测试
 * @Date 2020/7/16 16:37
 * @Author Danrbo
 */
public class TcpClient {
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
                        socketChannel.pipeline().addLast(new MessageProtocolEncoder());
                        socketChannel.pipeline().addLast(new MessageProtocolDecoder());
                        socketChannel.pipeline().addLast(new MyClientHandler());// 在管道配置上自定义的处理器
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

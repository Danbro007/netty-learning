package netty.http;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Classname TestServer
 * @Description TODO
 * @Date 2020/7/10 14:56
 * @Author Danrbo
 */
public class TestServer {
    public static void main(String[] args) {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MyChannelInitializer());
        System.out.println("服务器启动。。。。");
        try {
            ChannelFuture sync = bootstrap.bind(8888).sync();
            sync.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }
}

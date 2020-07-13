package netty.groupchat;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;

import java.util.Scanner;

/**
 * @Classname NettyGroupChatClient
 * @Description TODO
 * @Date 2020/7/13 15:31
 * @Author Danrbo
 */
public class NettyGroupChatClient {
    private int port;
    private String host;

    public NettyGroupChatClient() {
        this.port = 6666;
        this.host = "localhost";
    }

    public void run() {
        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new NettyGroupChatClientHandler());
                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect(this.host, this.port).sync();
            channelFuture.addListener((ChannelFutureListener) future -> {
                if (future.isSuccess()) {
                    System.out.println("连接服务器成功！");
                } else {
                    System.out.println("'连接服务器失败！");
                }
            });
            // 接收输入的文字，到 channel 发送文字。
            Channel channel = channelFuture.channel();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String nextLine = scanner.nextLine();
                channel.writeAndFlush(Unpooled.copiedBuffer(nextLine, CharsetUtil.UTF_8));
            }
        } catch (InterruptedException e) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) {
        NettyGroupChatClient client = new NettyGroupChatClient();
        client.run();
    }
}

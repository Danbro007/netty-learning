package netty.simple;


import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Netty快速入门实例-TCP服务
 * 实例要求：
 * 1、使用IDEA 创建Netty项目
 * 2、Netty 服务器在 6668 端口监听，客户端能发送消息给服务器 "hello, 服务器~"
 * 3、服务器可以回复消息给客户端 "hello, 客户端~"
 * 4、目的：对Netty 线程模型 有一个初步认识, 便于理解Netty 模型理论
 * 5、看老师代码演示
 *      5.1 编写服务端
 *      5.2 编写客户端
 *      5.3 对 netty 程序进行分析，看看 netty 模型特点
 */
public class NettyServer {
    public static void main(String[] args) throws InterruptedException {
        /**
         * 创建 bossGroup 和 workGroup
         * 1、bossGroup 负责处理连接请求，workGroup 负责真正的业务处理
         * 2、这两个都是无限循环
         */
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
        NioEventLoopGroup workGroup = new NioEventLoopGroup(1);
        // 创建服务器对象，用来配置参数
        ServerBootstrap bootstrap = new ServerBootstrap();
        //配置两个线程组
        bootstrap.group(bossGroup,workGroup)
                // 使用ServerSocketChannel来当做通道实现
                .channel(NioServerSocketChannel.class)
                // 设置线程队列等待连接的个数
                .option(ChannelOption.SO_BACKLOG,128)
                //设置保持活动连接的状态
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        // 给 workGroup 里的 eventloop 对应的管道设置自定义的处理器
                        socketChannel.pipeline().addLast(new NioServerHandler());
                    }
                });
        System.out.println("服务器启动。。。。");
        //设置端口,并同步生成一个 ChannelFuture 对象
        ChannelFuture channelFuture = bootstrap.bind(6666).sync();
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            public void operationComplete(ChannelFuture future) throws Exception {
                if (future.isSuccess()){
                    System.out.println("端口绑定成功！");
                }else {
                    System.out.println("端口绑定失败！");
                }
            }
        });
        channelFuture.channel().closeFuture().sync();// 对关闭通道进行监听，异步执行。
    }
}

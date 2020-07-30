package netty.rpc.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Classname NettyClient
 * @Description TODO
 * @Date 2020/7/27 14:29
 * @Author Danrbo
 */
public class NettyClient {

    // 建立一个线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    // 计数器
    private int count = 0;
    private static NettyClientHandler client;

    // 返回一个 serviceClass 的代理对象
    public Object getBean(final Class<?> serviceClass, final String providerName) {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    System.out.println("(proxy, method, args) 进入...." + (++count) + " 次");

                    if (client == null) {
                initClient();
            }
            client.setParam(providerName + args[0]);
            return executorService.submit(client).get();
        });
    }

    public static void initClient() {
        client = new NettyClientHandler();
        NioEventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);

                    }
                });
        try {
            ChannelFuture channelFuture = bootstrap.connect("localhost", 8088).sync();
            channelFuture.addListener((ChannelFutureListener) f->{
                if (f.isSuccess()){
                    System.out.println("连接成功");
                }else {
                    System.out.println("连接失败");
                }
            });

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}

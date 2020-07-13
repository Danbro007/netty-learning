package netty.http;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @Classname MyInitializer
 * @Description TODO
 *
 * 通道初始化器，在初始化的时候先加载 HttpServerCodec 和 自定义的 Handler。
 *
 * @Date 2020/7/10 15:01
 * @Author Danrbo
 */
public class MyChannelInitializer extends ChannelInitializer {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // HttpServerCodec 封装了解码器和编码器。
        pipeline.addLast("HttpServerCodec",new HttpServerCodec());
        pipeline.addLast("MyHttpServerHandler",new MyHttpServerHandler());
    }
}

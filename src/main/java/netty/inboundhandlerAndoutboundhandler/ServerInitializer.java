package netty.inboundhandlerAndoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Classname ServerInitializer
 * @Description TODO
 * @Date 2020/7/15 13:18
 * @Author Danrbo
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 配置解码器
        pipeline.addLast("Decoder",new ByteToLongDecoder2());
        // 配置编码器
        pipeline.addLast("Encoder",new LongToByteEncoder());
        pipeline.addLast(new ServerHandler());
    }
}

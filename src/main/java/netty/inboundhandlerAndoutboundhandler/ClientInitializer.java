package netty.inboundhandlerAndoutboundhandler;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

/**
 * @Classname ClientInitializer
 * @Description TODO
 * @Date 2020/7/15 15:49
 * @Author Danrbo
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        // 配置自定义的编码器 编码 Long 类型
        pipeline.addLast(new LongToByteEncoder());
        // 配置解码器
        pipeline.addLast(new ByteToLongDecoder2());
        // 自定义处理器
        pipeline.addLast(new ClientHandler());
    }
}

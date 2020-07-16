package netty.inboundhandlerAndoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Classname MyDecoder
 * @Description TODO
 * 把接收到的字节转换成 Long
 * @Date 2020/7/15 13:22
 * @Author Danrbo
 */
public class ByteToLongDecoder2 extends ReplayingDecoder<Void> {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码器2被调用！");
        out.add(in.readLong());
    }
}

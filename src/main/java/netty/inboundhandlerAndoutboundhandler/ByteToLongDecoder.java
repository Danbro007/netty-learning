package netty.inboundhandlerAndoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @Classname MyDecoder
 * @Description TODO
 * 把接收到的字节转换成 Long
 * @Date 2020/7/15 13:22
 * @Author Danrbo
 */
public class ByteToLongDecoder extends ByteToMessageDecoder {

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        System.out.println("解码器被调用！");
        // 由于一个 Long 类型的字节大小 8 个字节 所以我们判断接受的数据是不是 8 个字节
        if (in.readableBytes() >= 8 ){
            out.add(in.readLong());
        }
    }
}

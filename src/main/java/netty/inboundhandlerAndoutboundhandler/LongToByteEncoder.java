package netty.inboundhandlerAndoutboundhandler;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @Classname LongToByteEncoder
 * @Description TODO
 * @Date 2020/7/15 15:50
 * @Author Danrbo
 */
public class LongToByteEncoder extends MessageToByteEncoder<Long> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Long msg, ByteBuf out) throws Exception {
        System.out.println("编码器被调用!");
        System.out.println("msg=" + msg);
        out.writeLong(msg);
    }
}

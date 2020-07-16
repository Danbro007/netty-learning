package netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Classname MessageProtocolDecoder
 * @Description TODO
 * @Date 2020/7/16 20:35
 * @Author Danrbo
 */
public class MessageProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int length = in.readInt();
        byte[] content = new byte[length];
        in.readBytes(content);
        MessageProtocol messageProtocol = new MessageProtocol(length, content);
        out.add(messageProtocol);
    }
}

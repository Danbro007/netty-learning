package netty.tcp.protocoltcp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @Classname MessageProtocolDecoder
 * @Description TODO 消息协议解码器
 * @Date 2020/7/16 20:35
 * @Author Danrbo
 */
public class MessageProtocolDecoder extends ReplayingDecoder<Void> {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 获取消息长度
        int length = in.readInt();
        // 消息的内容
        byte[] content = new byte[length];
        // 读取消息内容
        in.readBytes(content);
        // 封装成MessageProtocol对象
        MessageProtocol messageProtocol = new MessageProtocol(length, content);
        // 交给后面的处理器处理
        out.add(messageProtocol);
    }
}

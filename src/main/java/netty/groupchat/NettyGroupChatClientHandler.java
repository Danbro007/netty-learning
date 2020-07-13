package netty.groupchat;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Classname NettyGroupChatClientHandler
 * @Description TODO
 * @Date 2020/7/13 15:41
 * @Author Danrbo
 */
public class NettyGroupChatClientHandler extends SimpleChannelInboundHandler {

    // 打印接收的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(byteBuf.toString(CharsetUtil.UTF_8));
    }
}

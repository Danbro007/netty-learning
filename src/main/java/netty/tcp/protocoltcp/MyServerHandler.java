package netty.tcp.protocoltcp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

import java.util.UUID;

/**
 * @Classname MyServerHandler
 * @Description TODO
 * @Date 2020/7/16 16:29
 * @Author Danrbo
 */
public class MyServerHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println(String.format("\n\n接收到【%s】发送的消息：%s", ctx.channel().remoteAddress(),new String(msg.getContent())));
        System.out.println("消息大小：" + msg.getLength());
        System.out.println(String.format("接收到的消息数：%s", ++this.count));
        // 返回随机的内容给客户端
        String responseContent = UUID.randomUUID() + " ";
        MessageProtocol messageProtocol = new MessageProtocol(responseContent.getBytes(CharsetUtil.UTF_8).length, responseContent.getBytes(CharsetUtil.UTF_8));
        ctx.writeAndFlush(messageProtocol);
    }
}

package netty.inboundhandlerAndoutboundhandler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @Classname ServerHandler
 * @Description TODO
 * @Date 2020/7/15 16:13
 * @Author Danrbo
 */
public class ServerHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(String.format("接收到【%s】发送来的数据【%s】", ctx.channel().remoteAddress(),msg));
        System.out.println(String.format("发送消息给客户端【%s】", ctx.channel().remoteAddress()));
        ctx.writeAndFlush(56789L);
    }
}

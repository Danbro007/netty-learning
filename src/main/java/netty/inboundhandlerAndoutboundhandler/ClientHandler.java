package netty.inboundhandlerAndoutboundhandler;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Classname ClientHandler
 * @Description TODO
 * @Date 2020/7/15 16:18
 * @Author Danrbo
 */
public class ClientHandler extends SimpleChannelInboundHandler<Long> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("发送数据给服务端");
        // 发送一个 Long 类型数据
        ctx.writeAndFlush(123456L);
//        ctx.writeAndFlush(Unpooled.copiedBuffer("aaaaaaaaaaaaaaaaa", CharsetUtil.UTF_8));
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Long msg) throws Exception {
        System.out.println(String.format("接收到服务端【%s】 发送的消息：%s", ctx.channel().remoteAddress(),msg));
    }
}

package netty.tcp.stickypackets;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Classname MyClientHandler
 * @Description TODO
 * @Date 2020/7/16 16:37
 * @Author Danrbo
 */
public class MyClientHandler extends SimpleChannelInboundHandler<ByteBuf> {
    private int count;
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        // 向客户端发送 10 次数据
        for (int i = 0; i < 10; i++) {
            System.out.println(String.format("发送第【%s】次", i));
            ctx.writeAndFlush(Unpooled.copiedBuffer(("【hello server " + i + "】"), CharsetUtil.UTF_8));
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.println(String.format("接收到服务端的消息：%s ", msg.toString(CharsetUtil.UTF_8)));
        System.out.println(String.format("接收到的消息数：%s", ++this.count));
    }
}

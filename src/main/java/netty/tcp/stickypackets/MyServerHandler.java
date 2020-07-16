package netty.tcp.stickypackets;

import io.netty.buffer.ByteBuf;
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
public class MyServerHandler extends SimpleChannelInboundHandler<ByteBuf> {
    /**
     * 接收数据次数计数器
     */
    private int count;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        // 接收到客户端发送的数据读取打印
        byte[] buffer = new byte[msg.readableBytes()];
        msg.readBytes(buffer);
        String string = new String(buffer, CharsetUtil.UTF_8);
        System.out.println(String.format("接收到【%s】发送的消息：%s", ctx.channel().remoteAddress(),string));
        System.out.println(String.format("接收到的消息数：%s", ++this.count));
        // 发送一个随机字符串到客户端
        ctx.writeAndFlush(Unpooled.copiedBuffer(UUID.randomUUID().toString() + " ",CharsetUtil.UTF_8));
    }
}

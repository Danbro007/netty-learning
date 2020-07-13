package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NioClientHandler extends ChannelInboundHandlerAdapter {

    // 当请求连接接上时的操作
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("client ctx=" +ctx);
        // 连接上发送消息
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,服务端！", CharsetUtil.UTF_8));
    }

    // 接收服务端发送的消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("来自服务器的信息：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("服务器地址：" + ctx.channel().remoteAddress());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}

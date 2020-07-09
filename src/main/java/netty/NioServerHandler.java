package netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

public class NioServerHandler extends ChannelInboundHandlerAdapter {

    // 接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("server ctx=" + ctx);
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("客户端发送的信息是：" + byteBuf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址来自：" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕的操作
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端！",CharsetUtil.UTF_8));
    }

    //出现异常关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }


}

package netty.websocket;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.time.LocalDateTime;

/**
 * @Classname MyWebSocketHandler
 * @Description TODO
 * @Date 2020/7/13 22:04
 * @Author Danrbo
 */
public class MyWebSocketHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        String text = msg.text();
        ctx.writeAndFlush(new TextWebSocketFrame(String.format("服务器时间【%s】---->%s", LocalDateTime.now(), text)));
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("【%s】上线", ctx.channel().remoteAddress()));
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("【%s】离线", ctx.channel().remoteAddress()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(String.format("【%s】连接出现异常:%s", ctx.channel().remoteAddress(), cause.getMessage()));
        ctx.channel().close();
    }
}

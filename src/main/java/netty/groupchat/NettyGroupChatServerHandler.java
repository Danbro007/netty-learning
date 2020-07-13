package netty.groupchat;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Classname ServerHandler
 * @Description TODO
 * @Date 2020/7/13 15:10
 * @Author Danrbo
 */
public class NettyGroupChatServerHandler extends SimpleChannelInboundHandler {
    /**
     * 存储所有的 channel ，channles 是单例模式
     */
    private static DefaultChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    /**
     * 时间格式
     */
    private static SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 接收客户端的数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println(String.format("服务器接收到消息 时间:%s--->【%s】发送的消息：%s",
                df.format(new Date()), ctx.channel().remoteAddress().toString().substring(1), byteBuf.toString(CharsetUtil.UTF_8)));
        System.out.println("服务器进行消息转发 ...");
        // 发送消息到 channels 里所有的 channel,除了发送消息的 channel
        channels.forEach(channel -> {
            if (channel != ctx.channel()) {
                String forwardMsg = String.format("【%s】说：%s", ctx.channel().remoteAddress(),byteBuf.toString(CharsetUtil.UTF_8));
                channel.writeAndFlush(Unpooled.copiedBuffer(forwardMsg,CharsetUtil.UTF_8));
            }
        });
    }

    // 当处理器被添加到当前 channel 的上下文触发，既新的连接创建。
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("客户端【%s】加入聊天", ctx.channel().remoteAddress());
        channels.writeAndFlush((Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8)));
        channels.add(ctx.channel());
    }

    // 当前 channel 的上下文删除处理器触发，既连接关闭。
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String msg = String.format("客户端【%s】离线！", ctx.channel().remoteAddress());
        channels.writeAndFlush(Unpooled.copiedBuffer(msg, CharsetUtil.UTF_8));
    }
    // 连接成功触发的事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("客户端【%s】上线！", ctx.channel().remoteAddress()));
    }
    // 连接断开触发的事件
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(String.format("客户端【%s】离开了！", ctx.channel().remoteAddress()));
    }
    // 连接异常触发的事件
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println(String.format("客户端【%s】出现连接错误！", ctx.channel().remoteAddress()));
        ctx.close();
    }
}

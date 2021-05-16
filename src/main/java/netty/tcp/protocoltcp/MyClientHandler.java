package netty.tcp.protocoltcp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.CharsetUtil;

/**
 * @Classname MyClientHandler
 * @Description TODO
 * @Date 2020/7/16 16:37
 * @Author Danrbo
 */
public class MyClientHandler extends SimpleChannelInboundHandler<MessageProtocol> {
    private int count;


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageProtocol msg) throws Exception {
        System.out.println("\n\n");
        System.out.println("接收到服务端发送的数据：" + new String(msg.getContent()));
        System.out.println("接收到的数据大小：" + msg.getLength());
        System.out.println(String.format("接收的次数：%s", ++this.count));
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("客户端发送数据");
        for (int i = 0; i < 10; i++) {
            // 回复数据给服务器
            String mes = "【hello server " + i + "】";
            MessageProtocol messageProtocol = new MessageProtocol(mes.getBytes(CharsetUtil.UTF_8).length, mes.getBytes(CharsetUtil.UTF_8));
            ctx.writeAndFlush(messageProtocol);
        }
    }
}

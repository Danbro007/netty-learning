package netty.rpc.netty;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.rpc.provider.HelloServiceImpl;

/**
 * @Classname NettyServerHandler
 * @Description TODO
 * @Date 2020/7/27 13:40
 * @Author Danrbo
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    private final static String PROVIDER_NAME = "HelloService#hello#";
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("msg = " + msg);
        if (msg.toString().startsWith(PROVIDER_NAME)){
            String responseMsg = new HelloServiceImpl().hello(msg.toString().substring(msg.toString().lastIndexOf('#') + 1));
            ctx.writeAndFlush(responseMsg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}

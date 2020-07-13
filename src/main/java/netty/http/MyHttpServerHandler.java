package netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @Classname MyHandler
 * @Description TODO
 * 快速入门实例-HTTP服务：
 * 1、实例要求：使用IDEA 创建Netty项目
 * 2、Netty 服务器在 8888 端口监听，浏览器发出请求 "http://localhost:8888/ "
 * 3、服务器可以回复消息给客户端 "你好，我是服务器 " ,  并对特定请求资源进行过滤。
 * 4、目的：Netty 可以做 Http 服务开发，并且理解 Handler 实例和客户端及其请求的关系.
 * @Date 2020/7/10 14:58
 * @Author Danrbo
 */
public class MyHttpServerHandler extends SimpleChannelInboundHandler {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("pipline hashCode:" + ctx.pipeline().hashCode());
            System.out.println("handler hashCode:" + ctx.handler().hashCode());
            System.out.println("msg的类型：" + msg.getClass());
            System.out.println("客户端地址：" + ctx.channel().remoteAddress());
            HttpRequest request = (HttpRequest) msg;
            // 如果请求的 uri 是 /favicon.ico 则不作响应
            if ("/favicon.ico".equals(request.uri())){
                System.out.println("对 /favicon.ico 的请求不作响应");
                return;
            }
            // 设置返回内容 设置为 UTF-16 ，如果是 UTF-8会有乱码
            ByteBuf content = Unpooled.copiedBuffer("你好，我是服务器", CharsetUtil.UTF_16);
            // 创建一个 Response，封装了 Http 版本、Http 响应码和响应的内容。
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            // 设置响应的内容类型
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            // 设置响应的内容长度
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());
            // 把响应写入通道返回
            ctx.writeAndFlush(response);
        }
    }
}

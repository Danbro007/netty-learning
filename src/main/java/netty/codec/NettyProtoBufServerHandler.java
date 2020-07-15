package netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyProtoBufServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 获取接收到的 Student 对象
        StudentPOJO.Student student = (StudentPOJO.Student) msg;
        System.out.println(student.getId() + ":" + student.getName());
    }
}

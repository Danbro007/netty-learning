package netty.codec;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyProtoBufClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 创建 Student 对象
        StudentPOJO.Student student = StudentPOJO.Student.newBuilder().setId(4).setName("Danbro").build();
        // 传输这个对象
        ctx.writeAndFlush(student);
    }
}

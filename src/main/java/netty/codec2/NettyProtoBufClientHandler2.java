package netty.codec2;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import netty.codec.StudentPOJO;

import java.util.Random;

public class NettyProtoBufClientHandler2 extends ChannelInboundHandlerAdapter {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 随机数 在 0、1、2
        int randomNum = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage;
        // 如果随机数是 0 则传输 Student 对象，如果是 1 则传输 Worker 对象，如果是 3 则传输 null。
        if (0 == randomNum) {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.StudentType)
                    .setStudent(MyDataInfo.Student.newBuilder().setId(100).setName("John").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder().setDataType(MyDataInfo.MyMessage.DataType.WorkerType)
                    .setWorker(MyDataInfo.Worker.newBuilder().setId(100).setAge(25).build()).build();
        }

        // 传输这个对象
        ctx.writeAndFlush(myMessage);
    }
}

package netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class NioServerHandler extends ChannelInboundHandlerAdapter {

    // 接收消息
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        //设置日期格式
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long start = System.currentTimeMillis();
        System.out.println("处理" + ctx.channel().remoteAddress() + "请求的线程是：" + Thread.currentThread().getName() + "----->" + df.format(new Date()));
        // 【1】模拟线程阻塞
//        Thread.sleep((long) (Math.random()*100000));
//        System.out.println("server ctx=" + ctx);
//        // 把接收的消息转换成 ByteBuf 类型
//        ByteBuf byteBuf = (ByteBuf) msg;
//        // 把消息转换成 UTF-8 并打印
//        System.out.println("客户端发送的信息是：" + byteBuf.toString(CharsetUtil.UTF_8));
//        // 打印发送消息客户端的地址
//        System.out.println("客户端地址来自：" + ctx.channel().remoteAddress());

        //【2】 测试把阻塞任务放入 TaskQueue
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                System.out.println("第一次阻塞：" + Thread.currentThread().getName() + "---->" + ctx.channel().remoteAddress() + "----->" + df.format(new Date()));
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("阻塞任务1!", CharsetUtil.UTF_8));
//        });
//        ctx.channel().eventLoop().execute(() -> {
//            try {
//                System.out.println("第二次阻塞：" + Thread.currentThread().getName() + "---->" + ctx.channel().remoteAddress() + "----->" + df.format(new Date()));
//                TimeUnit.SECONDS.sleep(10);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            ctx.writeAndFlush(Unpooled.copiedBuffer("阻塞任务2!", CharsetUtil.UTF_8));
//            System.out.println("处理请求的时间: " + (System.currentTimeMillis() - start) + " ms------>" + ctx.channel().remoteAddress());
//        });
        // 【3】 测试把任务放入 SchedleTask
        ctx.channel().eventLoop().schedule(()->{
            ctx.writeAndFlush(Unpooled.copiedBuffer("延时任务!", CharsetUtil.UTF_8));
        },5,TimeUnit.SECONDS);

        System.out.println("继续执行" + ctx.channel().remoteAddress());
    }

    // 数据读取完毕的操作
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        // 对发送的数据进行编码
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello,客户端！", CharsetUtil.UTF_8));
    }

    //出现异常关闭通道
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}

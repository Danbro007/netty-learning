package netty.unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class NettyByteBuf01 {
    public static void main(String[] args) {

        /*
        说明:
        1)读取数据geteByte(i) , 需要指定i
        2)读取数据readByte() , 不需要指定i， 内部的 readIndex 会一定
        3)通过Dubeug 看一下 readIndex writeIndex 和 capacity
        4)大家看到和NIO的Buffer 比较，不再需要flip 进行读写切换了，内部通过readIndex 和 writeIndex 来控制读和写操作
         */
        // 缓冲区容量为 10
        ByteBuf buffer = Unpooled.buffer(10);

        // 写入10个数字
        for (int i = 0; i < 10; ++i) {
            buffer.writeByte(i);
        }
        // 打印缓冲区里的数据
        for (int i = 0; i < buffer.capacity(); ++i) {
            System.out.println(buffer.getByte(i));
        }


//        for(int i = 0; i < buffer.capacity(); ++i) {
//            System.out.println(buffer.readByte());
//        }

        System.out.println(buffer.readByte());
    }
}

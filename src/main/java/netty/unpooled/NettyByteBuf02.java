package netty.unpooled;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

import java.nio.charset.Charset;

public class NettyByteBuf02 {
    public static void main(String[] args) {
        ByteBuf byteBuf = Unpooled.copiedBuffer("hello,world!", CharsetUtil.UTF_8);

        if (byteBuf.hasArray()) {
            byte[] content = byteBuf.array();
            // hello,world!
            System.out.println(new String(content, CharsetUtil.UTF_8));
            // UnpooledByteBufAllocator$InstrumentedUnpooledUnsafeHeapByteBuf(ridx: 0, widx: 12, cap: 36)
            System.out.println(byteBuf);
            // 0
            System.out.println(byteBuf.arrayOffset());
            // 0
            System.out.println(byteBuf.readerIndex());
            // 12
            System.out.println(byteBuf.writerIndex());
            // 36
            System.out.println(byteBuf.capacity());
            // 12
            int length = byteBuf.readableBytes();

            System.out.println(length);
            // readableBytes 表示可读的字节，既现在还有未读取的字节数。
            for (int i = 0; i < byteBuf.readableBytes(); ++i) {
                System.out.println((char) byteBuf.getByte(i));
            }
            // 从索引 0 开始往后取 4 个 字节 hell
            System.out.println(byteBuf.getCharSequence(0, 4, CharsetUtil.UTF_8));
            // 从索引 4 开始往后取 6 个字节 o,worl
            System.out.println(byteBuf.getCharSequence(4, 6, CharsetUtil.UTF_8));
        }
    }
}

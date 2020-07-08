package nio;

import java.nio.ByteBuffer;

/**
 * @Classname BufferReadOnlyTest
 * @Description TODO
 *
 * 测试只读缓冲区
 *
 * @Date 2020/7/7 12:10
 * @Author Danrbo
 */
public class BufferReadOnlyTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putChar('大');
        // 创建一个只读的缓冲区
        ByteBuffer readOnlyBuffer = byteBuffer.asReadOnlyBuffer();
        readOnlyBuffer.putChar('下');
    }
}

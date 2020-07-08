package nio;

import java.nio.ByteBuffer;

/**
 * @Classname BufferUnderflowExceptionTest
 * @Description TODO
 * 测试 BufferUnderflowException 异常
 * @Date 2020/7/7 12:03
 * @Author Danrbo
 */
public class BufferUnderflowExceptionTest {
    public static void main(String[] args) {
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byteBuffer.putInt(100);
        byteBuffer.putLong(9);
        byteBuffer.putChar('大');
        byteBuffer.putShort((short) 4);
        byteBuffer.flip();
        System.out.println(byteBuffer.getShort());
        System.out.println(byteBuffer.getInt());
        System.out.println(byteBuffer.getLong());
        System.out.println(byteBuffer.getLong());
    }

}

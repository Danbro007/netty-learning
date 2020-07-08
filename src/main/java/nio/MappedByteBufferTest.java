package nio;

import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname MappedByteBufferTest
 * @Description TODO
 *
 * 测试 MappedByteBuffer，直接在内存修改文件。
 *
 * @Date 2020/7/7 12:13
 * @Author Danrbo
 */
public class MappedByteBufferTest {
    public static void main(String[] args) throws Exception {
        // 创建一个通道
        RandomAccessFile accessFile = new RandomAccessFile("file01.txt", "rw");
        FileChannel channel = accessFile.getChannel();
        /**
         * 1、获取一个MappedByteBuffer，指定 MappedByteBuffer 的模式，我们现在选择的是读写模式，
         * 2、position 是我们要修改的起始下标
         * 3、size 是我们从 position 开始往后的个数
         */
        MappedByteBuffer byteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 6);
        // 我们把第1字符修改成 H
        byteBuffer.put(0, (byte) 'H');
        // 我们把第5字符修改成 O
        byteBuffer.put(4, (byte) 'O');
    }
}

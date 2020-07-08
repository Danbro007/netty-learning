package nio;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NioBufferWriteTest
 * @Description TODO
 * 实例要求:
 * 1、使用前面学习后的ByteBuffer(缓冲) 和 FileChannel(通道)， 将 "hello,danbro" 写入到 file01.txt 中
 * 2、文件不存在就创建
 * @Date 2020/7/6 22:35
 * @Author Danrbo
 */

public class NioBufferWriteTest {
    public static void main(String[] args) throws Exception {
        String str = "hello,danbro";
        // 创建一个输出流
        FileOutputStream fileOutputStream = new FileOutputStream("file01.txt");
        // 获取通道
        FileChannel channel = fileOutputStream.getChannel();
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(128);
        // 把字符串转成字节数组放入缓冲区里
        buffer.put(str.getBytes());
        // 把缓冲区切换成读取模式
        buffer.flip();
        // 把缓冲区的字节数组写入通道里
        channel.write(buffer);
        System.out.println("写入完成");
    }
}

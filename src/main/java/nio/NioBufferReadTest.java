package nio;

import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NioBufferReadTest
 * @Description TODO
 * 实例要求:
 * 1、使用前面学习后的ByteBuffer(缓冲) 和 FileChannel(通道)， 将 file01.txt 中的数据读入到程序，并显示在控制台屏幕
 * 2、假定文件已经存在
 * @Date 2020/7/6 23:04
 * @Author Danrbo
 */
public class NioBufferReadTest {
    public static void main(String[] args) throws Exception{
        // 把文件读取到内存中
        File file = new File("file01.txt");
        // 创建一个输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        // 从输入流获取 channel
        FileChannel channel = fileInputStream.getChannel();
        // 把 channel 里的数据写入到缓冲区
        channel.read(buffer);
        // 打印
        System.out.println(new String(buffer.array()));
        fileInputStream.close();
    }
}

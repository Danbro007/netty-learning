package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NioBufferCopyTest
 * @Description TODO
 * 应用实例3-使用一个Buffer完成文件读取
 * 实例要求:
 * 1、使用 FileChannel(通道) 和 方法  read , write，完成文件的拷贝
 * 2、拷贝一个文本文件 file01.txt , 放在项目下即可
 * @Date 2020/7/7 11:15
 * @Author Danrbo
 */
public class NioBufferCopyTest {
    public static void main(String[] args)throws Exception {
        File file = new File("file01.txt");
        // 创建文件输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建文件暑促胡流
        FileOutputStream fileOutputStream = new FileOutputStream("file02.txt");
        // 获取输入通道
        FileChannel inPutChannel = fileInputStream.getChannel();
        // 获取输入通道
        FileChannel outPutChannel = fileOutputStream.getChannel();
        // 创建一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate((int) file.length());
        // 把输入通道里的数据写入到缓冲区里
        inPutChannel.read(buffer);
        // 把缓冲区的状态切换成读取状态
        buffer.flip();
        // 把缓冲区里的数据写入到输出通道中
        outPutChannel.write(buffer);
    }
}

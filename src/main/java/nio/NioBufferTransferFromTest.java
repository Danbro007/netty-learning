package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname NioBufferTransferFromTest
 * @Description TODO
 * 应用实例4-拷贝文件transferFrom 方法
 * 实例要求:
 * 1、使用 FileChannel(通道) 和 方法  transferFrom ，完成文件的拷贝
 * 2、拷贝一张图片
 * @Date 2020/7/7 11:23
 * @Author Danrbo
 */
public class NioBufferTransferFromTest {
    public static void main(String[] args) throws Exception{
        File file = new File("suchmos.jpg");
        // 创建输入流
        FileInputStream fileInputStream = new FileInputStream(file);
        // 创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("newSuchmos.jpg");
        // 源文件的管道
        FileChannel sourceChannel = fileInputStream.getChannel();
        // 目标文件的管道
        FileChannel destChannel = fileOutputStream.getChannel();
        // 把源文件管道的数据传输到目标文件管道
        destChannel.transferFrom(sourceChannel,0,file.length());
    }
}

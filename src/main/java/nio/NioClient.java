package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @Classname NioClient
 * @Description TODO
 * 案例要求:
 * 1、编写一个 NIO 入门案例，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 2、目的：理解NIO非阻塞网络编程机制
 * @Date 2020/7/7 17:15
 * @Author Danrbo
 */
public class NioClient {
    public static void main(String[] args) throws Exception{
        // 设置连接的服务器地址和端口
        SocketChannel socketChannel = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // 如果没有连接上服务器则尝试重新连接
        if(!socketChannel.connect(inetSocketAddress)){
            while (!socketChannel.finishConnect()){
                System.out.println("因为需要连接时间，客户端不会阻塞，可以做其他事");
            }
        }
        String str = "hello,danbro";
        // 创建一个缓冲区 这个 wrap() 方法可以根据输入的字节大小自动调整缓冲区的大小
        ByteBuffer byteBuffer = ByteBuffer.wrap(str.getBytes());
        // 把缓冲区里的数据写入到通道里
        socketChannel.write(byteBuffer);
        System.in.read();
    }
}

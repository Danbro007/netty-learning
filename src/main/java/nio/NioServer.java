package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Classname NioServer
 * @Description TODO
 * 案例要求:
 * 1、编写一个 NIO 入门案例，实现服务器端和客户端之间的数据简单通讯（非阻塞）
 * 2、目的：理解NIO非阻塞网络编程机制
 * @Date 2020/7/7 14:54
 * @Author Danrbo
 */
    public class NioServer {
    public static void main(String[] args) throws Exception{
        // 创建一个 serverSocketChannel 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 获取一个 selector
        Selector selector = Selector.open();
        // 绑定端口
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 设置为非阻塞
        serverSocketChannel.configureBlocking(false);
        // 先把 serverSocketChannel 注册到 selector 中，事件为 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            // 如果 selector 里发生事件的通道数为 0 则 continue
            if (selector.select(2000) == 0){
                System.out.println("服务器等待了超过1秒钟，做其他事");
                continue;
            }
            // 说明有发生事件的通道
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            // 产生一个通道的迭代器
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){
                // 取出一个发生事件的通道
                SelectionKey selectionKey = iterator.next();
                // 如果这个事件是 OP_ACCEPT 类型的
                if (selectionKey.isAcceptable()){
                    System.out.println("接受到一个连接！");
                    // 接收请求的通道
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    // 设置为非阻塞
                    socketChannel.configureBlocking(false);
                    // 把这个请求注册进 selector 里，事件类型为 OP_READ ，并设置一个缓冲区
                    socketChannel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                // 如果这个事件类型是 OP_READ 类型的
                if (selectionKey.isReadable()){
                    // 把之前设置在 selectionKey 里的缓冲区获取到
                    ByteBuffer byteBuffer = (ByteBuffer) selectionKey.attachment();
                    // 获取通道
                    SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
                    // 把通道里的数据写入到缓冲区里
                    socketChannel.read(byteBuffer);
                    // 打印
                    System.out.println("接受到消息："+new String(byteBuffer.array()));
                }
                // 把刚刚从迭代器获取的 selectionKey 删除掉，防止之后再次重复获取。
                iterator.remove();
            }
        }
    }
}

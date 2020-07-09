package nio.groupchat;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Classname Server
 * @Description TODO
 * 实例要求:
 * 1. 编写一个 NIO 群聊系统，实现服务器端和客户端之间的数据简单通讯（非阻塞）。
 * 2. 实现多人群聊。
 * 3. 服务器端：可以监测用户上线，离线，并实现消息转发功能。
 * 4. 客户端：通过 channel 可以无阻塞发送消息给其它所有用户，同时可以接受其它用户发送的消息(有服务器转发得到)。
 * 5. 目的：进一步理解NIO非阻塞网络编程机制。
 * @Date 2020/7/8 10:17
 * @Author Danrbo
 */
@Data
public class Server {
    private final int PORT = 6666;
    private ServerSocketChannel serverSocketChannel;
    private Selector selector;




    public Server() throws Exception {
        this.serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(PORT);
        // 设置为非阻塞
        this.serverSocketChannel.configureBlocking(false);
        this.serverSocketChannel.socket().bind(inetSocketAddress);
        // 创建 selector
        this.selector = Selector.open();
        // 把 serverSocketChannel 注册进 selector，监听事件是 OP_ACCEPT
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        System.out.println("服务器启动。。。。");
        Selector selector = server.getSelector();
        while (true) {
            // 每隔 2 秒查看是否有事件发生
            if (selector.select(2000) == 0) {
                continue;
            }
            Set<SelectionKey> selectionKeys;
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                try {
                    if (next.isAcceptable()) {
                        server.accept();
                    }
                    if (next.isReadable()) {
                        server.readMsg(next);
                    }
                    if (next.isWritable()) {
                        selectionKeys = selector.keys();
                        SocketChannel socketChannel = (SocketChannel) next.channel();
                        selectionKeys.forEach(k -> {
                            SelectableChannel targetChannel = k.channel();
                            try {
                                if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
                                    SocketChannel channel = (SocketChannel) targetChannel;
                                    String msg = (String) next.attachment();
                                    channel.write(ByteBuffer.wrap(msg.getBytes()));
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        socketChannel.register(selector, SelectionKey.OP_READ);
                    }
                    iterator.remove();
                } catch (Exception e) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    System.out.println(channel.getRemoteAddress() + "---->下线");
                }
            }
        }
    }

    // 发送消息
    private void sendMsg(SocketChannel targetChannel, String msg) throws IOException {
        targetChannel.write(ByteBuffer.wrap(msg.getBytes()));
    }

    // 接收消息
    private void readMsg(SelectionKey selectionKey) throws Exception {
        SocketChannel channel = (SocketChannel) selectionKey.channel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        channel.read(byteBuffer);
        String msg = new String(byteBuffer.array());
        System.out.println(msg);
        channel.register(selector, SelectionKey.OP_WRITE, msg);
    }

    public void accept() throws Exception {
        SocketChannel socketChannel = this.serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.println("上线：" + socketChannel.getRemoteAddress());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }


}

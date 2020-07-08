package nio.groupchat;

import lombok.Data;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @Classname Client
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
public class Client {
    private final int PORT = 6666;
    private final String ADDRESS = "127.0.0.1";
    private SocketChannel socketChannel;
    private Selector selector;
    private String username;

    public Client() throws Exception {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(ADDRESS, PORT);
        this.socketChannel = SocketChannel.open(inetSocketAddress);
        this.socketChannel.configureBlocking(false);
        this.selector = Selector.open();
        this.socketChannel.register(selector, SelectionKey.OP_READ);
        this.username = socketChannel.getLocalAddress().toString();
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    try {
                        client.readMsg();
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
        while (true){
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()){
                String msg = scanner.nextLine();
                client.sendMsg(msg);
            }
        }


    }

    public void sendMsg(String msg) throws Exception {
        msg = username + "--发送的消息:" + msg;
        ByteBuffer byteBuffer = ByteBuffer.wrap(msg.getBytes());
        socketChannel.write(byteBuffer);
    }

    public void readMsg() throws IOException {
        if (selector.select() > 0) {
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey next = iterator.next();
                if (next.isReadable()) {
                    SocketChannel channel = (SocketChannel) next.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    channel.read(byteBuffer);
                    System.out.println(new String(byteBuffer.array()));
                }
                iterator.remove();
            }
        } else {
            System.out.println("目前没有可用的通道！");
        }
    }
}

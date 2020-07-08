package bio;

/**
 * @Classname BioTest
 * @Description TODO
 *  实例要求:
 *  1、使用 BIO 模型编写一个服务器端，监听 6666 端口，当有客户端连接时，就启动一个线程与之通讯。
 *  2、要求使用线程池机制改善，可以连接多个客户端.
 *  3、服务器端可以接收客户端发送的数据(telnet 方式即可)。
 * @Date 2020/7/6 21:30
 * @Author Danrbo
 */



import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class BioTest {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocket.bind(inetSocketAddress);
        // 线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        while (true){
            // 监听连接 阻塞状态
            Socket socket = serverSocket.accept();
            System.out.println("发现一个连接");
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    handle(socket);
                }
            });
        }
    }

    public static void handle(Socket socket) {
        // 缓冲
        byte[] buffer = new byte[1024];
        InputStream inputStream = null;
        try {
            inputStream = socket.getInputStream();
            while (true){
                int read = inputStream.read(buffer);
                if (read != -1){
                    System.out.println("接收消息："+new String(buffer,0,read));
                }else {
                    break;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                System.out.println("关闭连接！");
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}

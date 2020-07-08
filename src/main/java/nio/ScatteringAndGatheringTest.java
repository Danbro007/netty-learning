package nio;

        import java.net.InetSocketAddress;
        import java.nio.ByteBuffer;
        import java.nio.channels.ServerSocketChannel;
        import java.nio.channels.SocketChannel;
        import java.util.Arrays;

/**
 * @Classname ScatteringAndGatheringTest
 * @Description TODO
 * @Date 2020/7/7 12:27
 * @Author Danrbo
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws Exception {
        // 创建一个 ServerSocket 通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(6666);
        serverSocketChannel.socket().bind(inetSocketAddress);
        // 缓冲区组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);
        int max = 8;
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            int byteRead = 0;
            while (byteRead < max) {
                long read = socketChannel.read(byteBuffers);
                byteRead += read;
                Arrays.asList(byteBuffers).stream().map(b -> "position:" + b.position() + " limit:" + b.limit()).forEach(System.out::println);
            }
        }
    }
}

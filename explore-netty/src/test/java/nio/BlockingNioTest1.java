package nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockingNioTest1 {

    /**
     * 客户端
     */
    @Test
    public void client()throws Exception{
        //先来一个网络管道用于网络传输
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9200));

        //再来一个文件管道读取本地文件
        FileChannel fileInChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/1.pdf"), StandardOpenOption.READ);

        //再来一个缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //把缓冲区切换到读状态
        buffer.flip();
        //从文件管道中读数据，然后写到网络管道
        while (fileInChannel.read(buffer) != -1) {
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.close();
        fileInChannel.close();

    }

    /**
     * 服务端
     */
    @Test
    public void server()throws Exception{
        //来一个服务端网络管道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定监听的端口
        serverSocketChannel.bind(new InetSocketAddress(9200));

        //再来一个文件管道用来把数据写到本地
        FileChannel fileOutChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/2.pdf"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //从网络管道中读数据，写到文件管道中
        //先获取到连接的网络管道
        SocketChannel socketChannel = serverSocketChannel.accept();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.flip();
        //读连接的网络管道
        while (socketChannel.read(buffer) != -1) {
            fileOutChannel.write(buffer);
            buffer.clear();
        }
        serverSocketChannel.close();
        fileOutChannel.close();
        socketChannel.close();
    }
}

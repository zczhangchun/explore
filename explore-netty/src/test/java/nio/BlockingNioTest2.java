package nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

public class BlockingNioTest2 {

    @Test
    public void client()throws Exception{
        //创建客户端channel
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9200));

        //创建文件channel，读取本地文件
        FileChannel fileInChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/1.pdf"), StandardOpenOption.READ);

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //把本地文件的channel发给服务端
        while (fileInChannel.read(buffer) != -1) {
            socketChannel.write(buffer);
            buffer.clear();
        }

        socketChannel.shutdownOutput();

        //接收服务端发来的请求
        int len = 0;
        while ((len = socketChannel.read(buffer)) != -1){
            buffer.flip();
            System.out.println(new String(buffer.array(), 0, len));
            buffer.clear();
        }

        socketChannel.close();
        fileInChannel.close();
    }


    @Test
    public void server()throws Exception{
        //创建服务端channel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //创建文件channel，用来把客户端的数据存到本地
        FileChannel fileOutChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/2.pdf"), StandardOpenOption.WRITE, StandardOpenOption.CREATE);

        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9200));

        //获取客户端的连接
        SocketChannel socketChannel = serverSocketChannel.accept();

        //创建缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //切换成读没收，读取客户端的数据
        buffer.flip();
        //读取客户端发来的数据，存到本地
        while (socketChannel.read(buffer) != -1) {
            fileOutChannel.write(buffer);
            buffer.clear();
        }

        //给客户端一个反馈
        buffer.put("服务端接收数据成功".getBytes());
        buffer.flip();
        socketChannel.write(buffer);

        serverSocketChannel.close();;
        socketChannel.close();
        fileOutChannel.close();

    }
}

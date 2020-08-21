package nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TcpNioTest1 {


    @Test
    public void client()throws Exception{
        //获取socket连接
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9200));
        socketChannel.configureBlocking(false);
        //获取缓冲区
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        //发送数据到服务端
        buffer.put("服务端发送数据".getBytes());
        buffer.flip();
        socketChannel.write(buffer);
        buffer.clear();
        socketChannel.close();

    }

    @Test
    public void server()throws Exception{
        //获取服务端socket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //切换成非阻塞模式
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress(9200));

        //创建选择器
        Selector selector = Selector.open();
        //把服务端socket注册到selector，selector只关注服务端的连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //选择器轮询
            selector.select();
            //获取到有事件发生的socket，并遍历他们
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            if (iterator.hasNext()) {
                //如果是连接事件，说明有socket来连接服务端了
                SelectionKey socketKey = iterator.next();
                //判断socketKey此时的事件是什么类型
                if (socketKey.isAcceptable()){
                    //获取到来连接的socket
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //把这个socket注册到选择器上，并设置非阻塞
                    socketChannel.configureBlocking(false);
                    //只关注这个socket的读时间（站在服务端而言，如果socket写数据，那么服务端的这个socket就会有读事件发生）
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if (socketKey.isReadable()){
                    //读事件，就获取到读事件的socket
                    SocketChannel socketChannel = (SocketChannel) socketKey.channel();
                    //创建缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    //读取数据
                    while ( socketChannel.read(buffer) != -1) {
                        buffer.flip();
                        System.out.println(new String(buffer.array(), 0, buffer.limit()));
                        buffer.clear();
                    }

                }
                //取消此次事件的socket，代表这个事件已经处理完
                iterator.remove();
            }
        }

    }
}

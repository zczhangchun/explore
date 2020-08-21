package nio;

import org.junit.Test;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class TcpNioTest2 {

    @Test
    public void client()throws Exception{

        //获取socketChannel
        SocketChannel socketChannel = SocketChannel.open();
        //设置非阻塞
        socketChannel.configureBlocking(false);
        //连接服务端
        socketChannel.connect(new InetSocketAddress("localhost", 9200));
        Selector selector = Selector.open();
        //把socket注册到selector，selector只关注socket的连接事件
        socketChannel.register(selector, SelectionKey.OP_CONNECT);
        while (true){
             //轮询
            selector.select();
            //获取到有发生事件的socket
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey socketKey = iterator.next();
                //对有发生事件的socket，对他们的事件进行处理
                //判断事件的类型
                if (socketKey.isConnectable()){
                    //如果是连接事件，那么就判断是不是正在连接，如果是那么完成连接，只有finishConnect()才算真正的连接上
                    socketChannel = (SocketChannel) socketKey.channel();
                    if (socketChannel.isConnectionPending()) {
                        socketChannel.finishConnect();
                    }

                    //完成连接后，可以在发送写请求给服务端
                    //先创建一个缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    buffer.put("HelloServer, I'm client".getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    //此时发送完数据后，让socket关注读事件，因为一会服务端会反馈数据给socket
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if (socketKey.isReadable()){
                    socketChannel = (SocketChannel) socketKey.channel();

                    //创建缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(1024);

                    //去读取服务端的数据
                    socketChannel.read(buffer);
                    buffer.flip();
                    //打印服务端反馈的数据
                    System.out.println(new String(buffer.array(), 0, buffer.limit()));
                }
                iterator.remove();
            }
        }
    }


    @Test
    public void server()throws Exception{
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //绑定端口
        serverSocketChannel.bind(new InetSocketAddress("localhost", 9200));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建选择器
        Selector selector = Selector.open();
        //把服务端socket绑定到选择器，选择器只关注服务器socket的连接事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        while (true){
            //选择器轮询
            selector.select();
            //通过选择器获取有事件的socket
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey socketKey = iterator.next();
                //判断socket发生的事件类型
                if (socketKey.isAcceptable()) {
                    //连接事件
                    //获取连接的socket
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //把这个socket设置成非阻塞
                    socketChannel.configureBlocking(false);
                    //把这个socket注册到选择器，选择器只关注这个socket的读事件
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if (socketKey.isReadable()){
                    //读事件
                    //获取发生读事件的socket
                    SocketChannel socketChannel = (SocketChannel) socketKey.channel();
                    //创建缓冲区
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //读取数据
                    socketChannel.read(byteBuffer);
                    byteBuffer.flip();
                    //打印数据
                    System.out.println(new String(byteBuffer.array(), 0, byteBuffer.limit()));
                    //反馈数据给客户端，也就是反馈数据给socket
                    byteBuffer.clear();
                    byteBuffer.put("Hello Client, I'm Server ".getBytes());
                    byteBuffer.flip();
                    socketChannel.write(byteBuffer);
                }
                iterator.remove();
            }
        }

    }
}

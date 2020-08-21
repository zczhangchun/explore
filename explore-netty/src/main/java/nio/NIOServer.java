package nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    public static void main(String[] args)throws Exception {

        //创建serverSocketChannel，其实就是创建server
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //把server设置成非阻塞
        serverSocketChannel.configureBlocking(false);
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        //创建一个多路复用器
        Selector selector = Selector.open();
        //把server注册到多路复用器上，并告诉多路复用器只需要监听本server的ACCEPT事件。因此，多路复用器此时只会关注server的ACCEPT事件
        //而server什么时候会有ACCEPT事件呢？当客户端来连接server的时候，server就会产生一个ACCEPT事件，此时多路复用器就会监听到server有一个ACCEPT事件，就会对其进行处理
        SelectionKey serverKey = serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println(serverKey);
        while (true){
            System.out.println("等待事件发生");
            //多路复用器开始轮询监听channel的key，select是阻塞的，accept()也是阻塞的。
            int select = selector.select();
            System.out.println("有事件发生了");
            //从多路复用器中获取到有事件的server，selectedKeys就相当于是channel
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()){

                SelectionKey key = iterator.next();
                System.out.println(key);
                //删除本次已经处理的channel
                iterator.remove();
                handle(key);
            }
        }

    }

    private static void handle(SelectionKey key)throws Exception {

        if (key.isAcceptable()){
            //如果事件是连接事件，那么就进行连接
            System.out.println("客户端来连接啦");
            //获取到接收这个事件的人，当然就是服务端进行接收的啦。
            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
            //此时，通过服务端连接，连接后获取到客户端的channel
            SocketChannel clientChannel = serverSocketChannel.accept();
            //把客户端设置成非阻塞
            clientChannel.configureBlocking(false);
            //把客户端也注册到多路复用器上，客户端此时只对读事件感兴趣
            clientChannel.register(key.selector(), SelectionKey.OP_READ);
        }else if (key.isReadable()){
            //读事件
            System.out.println("有客户端的可读事件发生了，服务端要开始读数据啦。");
            //获取客户端的管道
            SocketChannel clientChannel = (SocketChannel) key.channel();
            //从客户端的管道中读数据
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = clientChannel.read(byteBuffer);
            if (read != -1){
                System.out.println("读取到客户端发送的数据：" + new String(byteBuffer.array(), 0, read));
            }
            ByteBuffer bufferToWrite = ByteBuffer.wrap("HelloClient".getBytes());
            clientChannel.write(bufferToWrite);
            key.interestOps(SelectionKey.OP_READ | SelectionKey.OP_WRITE);
        }else if (key.isWritable()){
            SocketChannel clientChannel = (SocketChannel) key.channel();
            System.out.println("服务端要写数据给客户端啦");
            key.interestOps(SelectionKey.OP_READ);

        }


    }
}

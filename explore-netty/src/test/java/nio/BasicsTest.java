package nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.SortedMap;

public class BasicsTest {




    /**
     * 编码和解码
     */
    @Test
    public void test9()throws Exception{
        Charset charset = Charset.forName("GBK");
        String str = "我要开始编码";
        //编码器
        CharsetEncoder charsetEncoder = charset.newEncoder();
        //解码器
        CharsetDecoder charsetDecoder = charset.newDecoder();
        //编码
        CharBuffer buffer = CharBuffer.allocate(1024);
        buffer.put(str.toCharArray());
        //切换成读
        buffer.flip();
        ByteBuffer encodeBuf = charsetEncoder.encode(buffer);
        for (int i = 0; i < 12; i++) {
            System.out.println(encodeBuf.get());
        }

        //解码，切换成读模式
        encodeBuf.flip();
        System.out.println("=======================");
        CharBuffer decodeBuf = charsetDecoder.decode(encodeBuf);
        System.out.println(decodeBuf.toString());
    }

    /**
     * 获取所有字符集
     */
    @Test
    public void test8(){
        SortedMap<String, Charset> map = Charset.availableCharsets();
        for (Map.Entry<String, Charset> entry : map.entrySet()) {
            System.out.println(entry.getKey() + "：" + entry.getValue());
        }
    }

    /**
     * 分散和聚集
     */
    @Test
    public void test7()throws Exception{
        //获取通道
        FileChannel inChannel = new RandomAccessFile("1.pdf", "rw").getChannel();

        //分散读取
        ByteBuffer[] byteBuffers = new ByteBuffer[19];
        for (int i = 0; i < byteBuffers.length; i++) {
            byteBuffers[i] = ByteBuffer.allocate(1024);
        }

        inChannel.read(byteBuffers);

        //聚集写入
        FileChannel outChannel = new RandomAccessFile("2.pdf", "rw").getChannel();

        outChannel.write(byteBuffers);
    }

    /**
     * 通道之间的数据传输（直接缓冲区）
     */
    @Test
    public void test6()throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/1.pdf"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/2.pdf"), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);

        inChannel.transferTo(0, inChannel.size(), outChannel);
        outChannel.transferFrom(inChannel, 0, inChannel.size());

        inChannel.close();
        outChannel.close();
    }

    /**
     * 使用FileChannel获取channel
     * 使用直接缓冲区完成文件的赋值
     * @throws Exception
     */
    @Test
    public void test5()throws Exception{
        //获取Input读通道
        FileChannel inChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/1.pdf"), StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/2.pdf"), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.READ);

        //创建Input的内存映射文件
        MappedByteBuffer inMapBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        //创建Out的内存映射文件，MapModel只有READ_WRITE，没有WRITE_ONLY
        MappedByteBuffer outMapBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        // 直接对内存映射文件进行读写
        byte[] bytes = new byte[inMapBuf.limit()];
        inMapBuf.get(bytes);
        outMapBuf.put(bytes);

        inChannel.close();
        outChannel.close();

    }

    /**
     * 使用FileInputStream获取channel
     * 使用非直接缓冲区
     * @throws Exception
     */
    @Test
    public void test4()throws Exception{
        //通过FileInputStream获取Input通道
        FileChannel inChannel = new FileInputStream("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/1.pdf").getChannel();
        //通过FileOutPutStream获取Out通道
        FileChannel outChannel = new FileOutputStream("/Users/zhangchun/IdeaProjects/explore/explore-netty/src/main/resources/2.pdf").getChannel();
        //来一个缓冲区，通道只是用来运输的，缓冲区才是来装东西的
        ByteBuffer buf = ByteBuffer.allocate(1024);
        while (inChannel.read(buf) != -1) {
            //切换到写状态
            buf.flip();
            //把缓冲区的数据写到管道中
            outChannel.write(buf);
            buf.clear(); //清空缓冲区
        }
        inChannel.close();
        outChannel.close();

    }

    /**
     * 直接缓冲区使用
     */
    @Test
    public void test3(){
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());
    }


    /**
     * 缓冲区的mark与reset使用
     */
    @Test
    public void test2(){
        ByteBuffer buf = ByteBuffer.allocate(1024);
        String str = "abcde";
        System.out.println("-------put()--------");
        buf.put(str.getBytes());
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        buf.mark();
        System.out.println("-------之前mark()--------");
        buf.put(str.getBytes());
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        System.out.println("-------mark()恢复后--------");
        buf.reset();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

    }

    /**
     * 基本API操作
     */
    @Test
    public void test1(){
        String str = "abcde";

        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("-------allocate()--------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //存数据
        System.out.println("---------put()-------");
        buf.put(str.getBytes());
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //先切换成读状态，切换成读状态其实就是把limit指向此时缓冲区可以操作到的最大位置。
        System.out.println("--------flip()--------");
        buf.flip();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());


        System.out.println("--------get()--------");
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //就是把position置为初始位0
        System.out.println("--------rewind()--------");
        buf.rewind();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //把position变为初始化位0，把limit变成初始化1024
        System.out.println("--------clear()--------");
        buf.clear();
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
    }

}

package bio;

import java.net.ServerSocket;
import java.net.Socket;

public class SocketServer {
    public static void main(String[] args)throws Exception {
        ServerSocket serverSocket = new ServerSocket(9000);
        System.out.println("服务端启动");
        //获取客户端的连接
        while (true) {
            Socket socket = serverSocket.accept();
            System.out.println("服务端获取到连接");
            new Thread(() -> {
                try {
                    handler(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } ).start();

        }


    }

    private static void handler(Socket socket)throws Exception {
        System.out.println("服务单开始读取数据");
        byte[] bytes = new byte[1024];

        int read = socket.getInputStream().read(bytes);
        if (read != -1){
            System.out.println("客户端数据：" + new String(bytes, 0, read));
        }
        //发条数据返回给客户端
        socket.getOutputStream().write("HelloClient".getBytes());
        socket.getOutputStream().flush();

    }
}

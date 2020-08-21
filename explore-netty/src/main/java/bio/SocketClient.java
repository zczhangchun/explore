package bio;


import java.io.OutputStream;
import java.net.Socket;

public class SocketClient {
    public static void main(String[] args)throws Exception {

        Socket socket = new Socket("localhost",9000);
        OutputStream outputStream = socket.getOutputStream();
        outputStream.write("HelloServer".getBytes());
        outputStream.flush();

        byte[] bytes = new byte[1024];
        int read = socket.getInputStream().read(bytes);
        if (read != -1){
            System.out.println("服务端发来消息：" +  new String(bytes, 0, read));
        }
        socket.close();

    }
}

package InetApi;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP1Server {
    public static void main(String[] args) throws Exception {
        ServerSocket server = new ServerSocket(9999);
        Socket socket = server.accept();

        byte[] bit = StreamUtils.streamToByteArray(socket.getInputStream());
        socket.shutdownInput();

        BufferedOutputStream out_imag = new BufferedOutputStream(new FileOutputStream(new File("x.png")));
        out_imag.write(bit);

        BufferedWriter out_info = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        out_info.write("接收图片成功");
        out_info.flush();
        socket.shutdownOutput();

        System.out.println("关闭服务器");

        out_info.close();
        out_imag.close();
        socket.close();
        server.close();
    }
}

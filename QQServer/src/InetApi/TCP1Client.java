package InetApi;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class TCP1Client {
    public static void main(String[] args) throws Exception {

        Socket socket = new Socket("localhost",9999);

        BufferedInputStream imag = new BufferedInputStream(new FileInputStream("1.png")) ;
        byte[] bit = StreamUtils.streamToByteArray(imag);

        BufferedOutputStream out_imag = new BufferedOutputStream(socket.getOutputStream());
        out_imag.write(bit);
        out_imag.close();
        socket.shutdownOutput();

        InputStream info = socket.getInputStream();
        System.out.println(StreamUtils.streamToString(info));
        socket.shutdownInput();

        System.out.println("客户端关闭");
        info.close();
        out_imag.close();
        imag.close();
        socket.close();
    }
}

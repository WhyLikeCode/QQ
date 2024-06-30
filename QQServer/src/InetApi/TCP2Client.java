package InetApi;

import java.io.*;
import java.net.Socket;

public class TCP2Client {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 9999);

        OutputStream music = socket.getOutputStream();
        music.write("ringing_short.mp3".getBytes());
        music.flush();

        socket.shutdownOutput();

        InputStream assign_music = socket.getInputStream();
        byte[] bits = StreamUtils.streamToByteArray(assign_music);
        socket.shutdownInput();

        OutputStream save_as = new FileOutputStream("123.mp3");
        save_as.write(bits);
        save_as.flush();

        System.out.print("客户端接收成功");

        save_as.close();
        assign_music.close();
        music.close();
        socket.close();

    }
}

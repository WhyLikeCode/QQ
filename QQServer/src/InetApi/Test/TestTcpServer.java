package InetApi.Test;

import InetApi.StreamUtils;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TestTcpServer {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(9998);
        Socket socket = server.accept();

        InputStream ac_data = socket.getInputStream();
        String info = StreamUtils.streamToString(ac_data);
        System.out.println(info);

        ac_data = socket.getInputStream();
        info = StreamUtils.streamToString(ac_data);
        System.out.println(info);
        socket.shutdownInput();

        ac_data.close();
        socket.close();
        server.close();
    }
}

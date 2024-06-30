package InetApi.Test;

import java.io.OutputStream;
import java.net.Socket;

public class TestTcpClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost",9998);
        OutputStream data = socket.getOutputStream();
        data.write("你好,我是顶真".getBytes());
        data.flush();
        data.close();
        socket.shutdownOutput();

        data = socket.getOutputStream();
        data.write("你好,我还是顶针".getBytes());
        data.flush();
        data.close();
        socket.shutdownOutput();

        socket.close();
    }
}

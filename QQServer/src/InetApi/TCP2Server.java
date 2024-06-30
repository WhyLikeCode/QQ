package InetApi;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class TCP2Server {
    public static void main(String[] args) throws Exception{
        ServerSocket server = new ServerSocket(9999);   //创建服务器socket对象，端口号为9999
        Socket socket = server.accept();        //与客户端建立连接

        InputStream ac_music =socket.getInputStream();
        String music = StreamUtils.streamToString(ac_music);
        socket.shutdownInput();

        File music_folder = new File("music");  //打开文件夹
        File music_file=null;
        File[] files = music_folder.listFiles();       //将music文件夹下的文件放到files文件数组中

        assert files != null : music_folder.getName()+"下没有文件";  //用assert(断言)判断music目录下有没有子文件,没有则抛出异常
        for(File file : files){
            if (file.getName().equals(music)){
                music_file=new File(music);     //创建客户指定音乐对象
            }
        }

        if (music_file == null){
            music_file = new File("music/兰亭序--周杰伦.mp3"); //如果没有客户指定的音乐，返回默认音乐
        }

        byte[] bits = StreamUtils.streamToByteArray(new FileInputStream(music_file));
        OutputStream send_music = socket.getOutputStream();
        send_music.write(bits);
        send_music.flush();
        socket.shutdownOutput();

        System.out.print("服务器发送成功");

        send_music.close();
        ac_music.close();
        socket.close();
        server.close();
    }
}

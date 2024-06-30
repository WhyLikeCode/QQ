package InetApi;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Api {
    public static void main(String[] args) throws UnknownHostException {
        //获取本机主机名/ip
        InetAddress localhost = InetAddress.getLocalHost();
        System.out.println("" + localhost);  //wanghaoyu/100.95.77.186 宽带断开后返回的以太网ip

        InetAddress host1 = InetAddress.getByName("wanghaoyu");
        System.out.println("" + host1); //wanghaoyu/100.95.77.186

        InetAddress host2 = InetAddress.getByName("www.baidu.com");
        System.out.println("" + host2); //www.baidu.com/36.155.132.3

        String ip = host2.getHostAddress();
        System.out.println(ip); //36.155.132.3

        String ip1 = host2.getHostName();
        System.out.println(ip1);    //www.baidu.com

        InetAddress[] localhost_ = InetAddress.getAllByName("wanghaoyu");   //获取所有地址包括IPv6地址
        for(InetAddress ita:localhost_){
            System.out.println(""+ita);
        }
        //wanghaoyu/100.95.77.186
        //wanghaoyu/169.254.107.210
        //wanghaoyu/192.168.183.1
        //wanghaoyu/192.168.230.1
        //wanghaoyu/fe80:0:0:0:a767:ed59:e71d:f1d8%11
        //wanghaoyu/fe80:0:0:0:22a9:b75b:e3de:72bc%4
        //wanghaoyu/fe80:0:0:0:98df:283:c291:d92e%14


    }
}

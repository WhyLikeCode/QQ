package com.why.qqclient.service;

import com.why.qqcommon.Message;
import com.why.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: 王浩雨
 * 创建客户端线程，并持有socket
 */

public class ClientConnectServerThread extends Thread{
    private Socket socket;  //用来接收客户端socket对象
    ClientConnectServerThread(Socket socket){   //构造方法
        this.socket = socket;
    }

    public void closeSocket() {
        try{
            this.socket.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    @Override
    public void run() {     //重写run方法


        while(true){

            try{

                //创建对象输入流，等待服务器发送对象信息
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)objectInputStream.readObject();

                if (message.getM_mesType().equals(MessageType.MESSAGE_RET_ONLINE_FRIEND)){ //判断返回的是否是在线用户列表

                    String[] onlineUsers = message.getM_content().split(" ");   //将信息分割成用户列表
                    System.out.print("\n======在线用户列表: ======\n");
                    for (String user:onlineUsers){
                        System.out.print("用户: " + user + "\n");     //打印在线用户
                    }
                }else if(message.getM_mesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    System.out.println("断开与服务器连接");
                    socket.close();
                    break;
                }else if(message.getM_mesType().equals(MessageType.MESSAGE_SEND_PRIVATE_INFO)){

                    System.out.print("\n" + message.getM_sender() + ": " + message.getM_content());
                    System.out.print("\n发送时间" + message.getM_sendTime() + "\n");

                } else{
                    System.out.println("其他信息");
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
    }
}

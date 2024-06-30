package com.why.qqserver.service;

import com.why.qqcommon.Message;
import com.why.qqcommon.MessageType;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: 王浩雨
 */
public class ServerConnectClientThread extends Thread{
    public Socket socket;
    public String userId;

    ServerConnectClientThread(Socket socket, String userId){
        this.socket = socket;
        this.userId = userId;
    }

    @Override
    public void run() {


        while(true){
            try{

                System.out.println("从用户( " + userId + " )接收到信息");
                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                Message message = (Message)objectInputStream.readObject();


                if (message.getM_mesType().equals(MessageType.MESSAGE_GET_ONLINE_FRIEND)){
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    //ObjectOutputStream objectOutputStream1 = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(message.getM_sender()).socket.getOutputStream());
                    System.out.println(message.getM_sender() + "请求得到在线用户列表");
                    message.setM_content(ManageServerConnectClientThread.getOnlineUsers()); //设置发送内容
                    message.setM_mesType(MessageType.MESSAGE_RET_ONLINE_FRIEND);    //将信息类型改为返回在线用户列表
                    message.setM_getter(message.getM_sender());     //将收件人改为发件人
                    message.setM_sender("server");
                    objectOutputStream.writeObject(message);    //发送在线用户列表信息
                    objectOutputStream.flush();

                }else if(message.getM_mesType().equals(MessageType.MESSAGE_CLIENT_EXIT)){
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    ManageServerConnectClientThread.removeUser(message.getM_sender());  //断开与客户端的连接
                    message.setM_sender("server");
                    message.setM_getter(userId);
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();
                    socket.close();
                    break;  //结束线程

                }else if(message.getM_mesType().equals(MessageType.MESSAGE_SEND_PRIVATE_INFO)){ //服务器转发来自客户端的信息
                    //获取到接收者的线程的socket，并创建输出流
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(message.getM_getter()).socket.getOutputStream());
                    //转发信息给接收者
                    objectOutputStream.writeObject(message);
                    objectOutputStream.flush();

                }else if(message.getM_mesType().equals(MessageType.MESSAGE_SEND_PUBLIC_INFO)){  //服务器转发给所有在线用户
                    message.setM_mesType(MessageType.MESSAGE_SEND_PRIVATE_INFO);    //群发就是私发给所有用户
                    for (String thread: ManageServerConnectClientThread.getOnlineUsers().split(" ")){   //获取在线用户
                        if (thread.equals(message.getM_sender())) continue;
                        ObjectOutputStream objectOutputStream = new ObjectOutputStream(ManageServerConnectClientThread.getServerConnectClientThread(thread).socket.getOutputStream());
                        objectOutputStream.writeObject(message);
                        objectOutputStream.flush();
                    }
                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }
        System.out.println("断开与用户(" + userId + "的连接");  //打印与那个客户端断开连接

    }
}

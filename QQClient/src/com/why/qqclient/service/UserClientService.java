package com.why.qqclient.service;

import com.why.qqclient.view.Utility;
import com.why.qqcommon.Message;
import com.why.qqcommon.MessageType;
import com.why.qqcommon.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: 王浩雨
 * 连接到服务器
 */

public class UserClientService {
    private static User user;
    private static Socket socket;
    private SimpleDateFormat sdf;

    public boolean checkUser(String userId, String userPwd, String s){
        user = new User(userId,userPwd);
        boolean status = false;
        try{

            socket = new Socket("192.168.230.1", 9999);     //连接服务器
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());       //创建对象输出流
            objectOutputStream.writeObject(user);       //将user对象发送出去
            objectOutputStream.flush();

            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());   //获取服务器发送的对象
            Message message = (Message)objectInputStream.readObject();  //强转为message对象

            //关闭对象输入输出流

            if (message.getM_mesType().equals(MessageType.MESSAGE_LOGIN_SUCCEED)){
                if (s.equals("1")){ //若为登录码，则创建并开启线程
                    System.out.println("成功与服务器建立连接!");
                    //创建线程，用来进行发送和接收数据
                    ClientConnectServerThread clientConnectServerThread = new ClientConnectServerThread(socket);
                    //将该线程加入到hash表中进行管理
                    ManageClientConnectServerThread.addClientConnectServerThread(userId, clientConnectServerThread);
                    //启动该线程
                    clientConnectServerThread.start();
                }else{  //若为注册码，则不开启线程，同时关闭socket
                    System.out.println("注册成功");
                    socket.close();
                }

                status = true;  //设置用户是否存在标志

            }else{
                socket.close();
            }


        }catch(Exception e){
            e.printStackTrace();
        }
        return status;
    }

    public void requestOnlineFriendList(){
        Message message = new Message();
        message.setM_mesType(MessageType.MESSAGE_GET_ONLINE_FRIEND);    //设置信息类型为请求获取在线用户列表
        message.setM_sender(user.getM_userId());    //设置发送人为客户端id
        message.setM_getter("server");  //设置接收端为服务器

        try{
            //通过管理线程类对象id获取线程，通过线程获取socket，通过socket获取输出流
            ObjectOutputStream objectOutputStream = new ObjectOutputStream
                    (ManageClientConnectServerThread.getClientConnectServerThread(user.getM_userId()).getSocket().getOutputStream());
            objectOutputStream.writeObject(message);    //将要求获取在线用户列表信息包发出
            objectOutputStream.flush();


        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void exitMain(){
        Message message = new Message();
        message.setM_sender(user.getM_userId());    //发送方为客户端id
        message.setM_getter("server");  //接收方为服务器
        message.setM_mesType(MessageType.MESSAGE_CLIENT_EXIT);  //设置信息类型为退出

        try{
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);    //发送退出信息包
            objectOutputStream.flush();
            ManageClientConnectServerThread.removeUser(user.getM_userId());

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void privateChat(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("======私聊信息,输n退出私聊=====");
        Message message = new Message();
        System.out.print("\n你想跟谁私聊:");
        String friend = Utility.readString();
        System.out.print("\n开始与" + friend + "对话\n");
        message.setM_sender(user.getM_userId());    //设置发送方id
        message.setM_getter(friend);        //设置接收方id
        message.setM_mesType(MessageType.MESSAGE_SEND_PRIVATE_INFO);    //设置消息类型为私聊

        while (true){

            String info = Utility.readString();
            if (info.equals("n") || info.equals("N")) break;
            message.setM_content(info); //设置发送内容
            message.setM_sendTime(sdf.format(new Date()));  //设置发送时间为当前系统时间
            try{

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(message);    //发送消息
                objectOutputStream.flush();

            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

    public void publicChat(){
        sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("\n======群聊信息,输n退出群聊=====");
        Message message = new Message();
        System.out.print("\n进入群聊\n");
        message.setM_sender(user.getM_userId());    //设置发送方id
        message.setM_getter("everybody");        //设置接收方id
        message.setM_mesType(MessageType.MESSAGE_SEND_PUBLIC_INFO);    //设置消息类型为私聊

        while (true){

            String info = Utility.readString();
            if (info.equals("n") || info.equals("N")) break;

            message.setM_content(info); //设置发送内容
            message.setM_sendTime(sdf.format(new Date()));  //设置发送时间为当前系统时间
            try{

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                objectOutputStream.writeObject(message);    //发送消息
                objectOutputStream.flush();

            }catch(IOException e){
                e.printStackTrace();
            }

        }
    }

}

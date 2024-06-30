package com.why.qqserver.service;

import com.why.qqcommon.Message;
import com.why.qqcommon.MessageType;
import com.why.qqcommon.User;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author: 王浩雨
 * 服务器，监听9999，与客户端保持连接
 */
public class QQServer {
    private ServerSocket server;        //获取服务器socket对象

    //HashMap并没有处理线程安全
    //ConcurrentHashMap处理线程安全，在并发的情况下是安全的
    private static ConcurrentHashMap<String, User> validUsers = new ConcurrentHashMap<>();  //存放用户信息

    static{ //添加初始用户
        validUsers.put("Why", new User("Why","123456"));
        validUsers.put("Jack", new User("Jack","123456"));
        validUsers.put("Jerry", new User("Jerry","123456"));
        validUsers.put("Tom", new User("Tom","123456"));
    }

    private boolean checkUser(String userId, String pwd){       //检查用户是否存在
        User user = validUsers.get(userId);
        if (user == null){
            return false;
        }

        return user.getM_passwd().equals(pwd);  //用户存在则判断密码是否正确
    }

    public QQServer(){

        try{

            //初始话用户列表
            BufferedReader br = new BufferedReader(new FileReader("UserInfo.txt"));
            String init;
            while((init=br.readLine())!=null){  //读每个用户信息
                String[] infos = init.trim().split(" ");    //分割成字符串数组
                validUsers.put(infos[0],new User(infos[0],infos[1]));   //写入到HashMap中
            }
            br.close();

            server = new ServerSocket(9999);    //监听端口为9999
            while(true) {

                Socket socket = server.accept();        //接收客户端socket对象

                ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
                User user = (User) objectInputStream.readObject();  //读取User对象
                if (validUsers.get(user.getM_userId()) == null){    //如果不在
                    validUsers.put(user.getM_userId(),user);    //注册完成后立即生效

                    FileWriter file = new FileWriter("UserInfo.txt", true);
                    BufferedWriter bw = new BufferedWriter(file);
                    bw.write(user.getM_userId() + " " + user.getM_passwd());    //将注册的用户名密码写入到用户信息文件中
                    bw.newLine();
                    bw.flush();
                    bw.close();
                    file.close();

                    Message message = new Message();    //发送是否注册成功的message对象
                    message.setM_mesType(MessageType.MESSAGE_LOGIN_SUCCEED);
                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
                    objectOutputStream.writeObject(message);    //将注册成功消息返回
                    objectOutputStream.flush();
                    socket.close(); //关闭套接字
                    continue;   //跳出当前程序
                }

                Message message = new Message();    //发送是否登录成功的message对象

                ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

                if (checkUser(user.getM_userId(),user.getM_passwd())) { //登录成功
                    System.out.println("用户" + user.getM_userId() + "登录成功");
                    message.setM_mesType(MessageType.MESSAGE_LOGIN_SUCCEED);    //用户名密码匹配，message设置为succeed

                    objectOutputStream.writeObject(message);    //将登录成功消息返回
                    objectOutputStream.flush();

                    ServerConnectClientThread serverConnectClientThread = new ServerConnectClientThread(socket, user.getM_userId());    //创建一个user线程
                    serverConnectClientThread.start();  //启动该线程
                    ManageServerConnectClientThread.addServerConnectClientThread(user.getM_userId(),serverConnectClientThread); //将该线程添加到userThread集合中

                } else { //登录失败
                    System.out.println("用户(" + user.getM_userId() + "登录失败");
                    message.setM_mesType(MessageType.MESSAGE_LOGIN_FAIL);   //用户名密码不匹配，message设置为fail
                    objectOutputStream.writeObject(message);    //返回登陆失败消息
                    objectOutputStream.flush();
                    socket.close();         //关闭套接字
                }


            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                server.close(); //关闭服务器套接字
            }catch(Exception e){
                e.printStackTrace();
            }
        }

    }
}

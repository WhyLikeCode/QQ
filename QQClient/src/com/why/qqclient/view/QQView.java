package com.why.qqclient.view;

import com.why.qqclient.service.ManageClientConnectServerThread;
import com.why.qqclient.service.UserClientService;
import com.why.qqcommon.User;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * @Author: 王浩雨
 */
public class QQView {
    private boolean loop;    //决定是否显示菜单
    private String key;     //获取用户选择键
    private UserClientService userClientService = new UserClientService();

    public static void main(String[] args) {
        new QQView().mainManu();
    }

    private void mainManu(){
        this.loop = true;       //循环主菜单
        while(loop){            //一级菜单
            System.out.println("==============欢迎登录网络通讯系统==============");
            System.out.println("\t\t 1 登录系统");
            System.out.println("\t\t 2 注册用户");
            System.out.println("\t\t 9 退出系统");
            System.out.print("请输入你的选择: ");
            this.key = Utility.readString();

            switch (key) {
                case "1" -> {
                    System.out.println("登陆系统: ");
                    System.out.print("输入用户名: ");
                    String userId = Utility.readString();
                    System.out.print("输入密码: ");
                    String pwd = Utility.readString();

                    if (userClientService.checkUser(userId,pwd,"1")) {       //登录服务器成功
                        System.out.println("==============欢迎(" + userId + ")==============");
                        while (loop) {        //登录成功后的二级菜单
                            System.out.println("===============网络通讯系统二级菜单(用户" + userId + ")==============");
                            System.out.println("\t\t 1 显示在线用户列表");
                            System.out.println("\t\t 2 群发消息");
                            System.out.println("\t\t 3 私聊消息");
                            System.out.println("\t\t 4 发送文件");
                            System.out.println("\t\t 9 退出系统");
                            System.out.print("请输入你的选择: ");
                            key = Utility.readString();

                            switch (key) {
                                case "1" -> {
                                    userClientService.requestOnlineFriendList();
                                }
                                case "2" -> userClientService.publicChat();//调用群聊方法
                                case "3" -> userClientService.privateChat();//调用私聊方法
                                case "4" -> System.out.println("发送文件");
                                case "9" -> {
                                    loop = false;
                                    userClientService.exitMain();
                                }
                            }
                        }
                    } else {      //登录服务器失败
                        System.out.println("用户或密码错误!");
                    }
                }
                case "2" -> {   //注册用户
                    System.out.print("请输入用户名: ");
                    String userId = Utility.readString();
                    System.out.print("请输入密码: ");
                    String pwd = Utility.readString();
                    if (! userClientService.checkUser(userId,pwd,"0")) {
                        System.out.println("该用户已经存在!");
                    }

                }
                case "9" -> this.loop = false;
            }
        }
    }

}

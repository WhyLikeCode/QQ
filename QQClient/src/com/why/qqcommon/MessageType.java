package com.why.qqcommon;

/**
 * @Author: 王浩雨
 */
public interface MessageType {
    String MESSAGE_LOGIN_SUCCEED = "1"; //登录成功
    String MESSAGE_LOGIN_FAIL = "2";    //登陆失败
    String MESSAGE_COMM_MES = "3";      //普通信息包
    String MESSAGE_GET_ONLINE_FRIEND = "4";   //要求返回在线用户列表
    String MESSAGE_RET_ONLINE_FRIEND = "5";   //返回在线用户列表
    String MESSAGE_CLIENT_EXIT = "6";         //客户端请求退出
    String MESSAGE_SEND_PRIVATE_INFO = "7";     //发送私聊信息
    String MESSAGE_SEND_PUBLIC_INFO = "8";      //群发信息
}

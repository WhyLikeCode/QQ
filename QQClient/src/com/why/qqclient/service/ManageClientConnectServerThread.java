package com.why.qqclient.service;

import java.util.HashMap;

/**
 * @Author: 王浩雨
 */

public class ManageClientConnectServerThread {
    //创建一个hash表来管理用户数据，主键为用户id，值为线程对象
    private static HashMap<String, ClientConnectServerThread> threadHash = new HashMap<>();

    public static void addClientConnectServerThread(String Id, ClientConnectServerThread clientConnectServerThread){
        threadHash.put(Id, clientConnectServerThread);
    }

    public static ClientConnectServerThread getClientConnectServerThread(String Id){
        return threadHash.get(Id);
    }
    public static void removeUser(String userId){
        threadHash.remove(userId);
    }

}

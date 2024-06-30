package com.why.qqserver.service;

import com.why.qqcommon.User;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * @Author: 王浩雨
 */
public class ManageServerConnectClientThread {
    private static HashMap<String, ServerConnectClientThread> userThread = new HashMap<>();
    public static void addServerConnectClientThread(String userId, ServerConnectClientThread clientTread){
        userThread.put(userId, clientTread);
    }

    public static ServerConnectClientThread getServerConnectClientThread(String userId){
        return userThread.get(userId);
    }

    public static String getOnlineUsers(){
        String userList = "";

        for (String user : userThread.keySet()){
            userList += user + " ";
        }

        return userList.trim();
    }

    public static void removeUser(String userId){
        userThread.remove(userId);
    }

}

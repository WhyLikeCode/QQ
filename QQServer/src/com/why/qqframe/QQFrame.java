package com.why.qqframe;

import com.why.qqserver.service.QQServer;

import java.io.File;
import java.io.IOException;

/**
 * @Author: 王浩雨
 */
public class QQFrame {
    public static void main(String[] args) throws IOException {
        System.out.println("等待客户端连接...");
        new QQServer();
    }
}

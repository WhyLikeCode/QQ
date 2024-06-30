package com.why.qqclient.view;

import java.util.Scanner;

/**
 * @Author: 王浩雨
 */
public class Utility {
    private static Scanner scanner = new Scanner(System.in);

    public static String readString(){
        return scanner.next();
    }

    public static int readInt(){
        return scanner.nextInt();
    }

}

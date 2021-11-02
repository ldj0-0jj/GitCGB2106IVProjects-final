package com.cy.net;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java中的网络编程会涉及两部分内容
 * 1)API (ServerSocket-服务端,Socket-客户端)
 * 2)协议 (TCP,UDP)
 */

public class Tomcat {//Nacos (8848)-application.properties
    static boolean flag=true;
    public static void main(String[] args) throws IOException {
         //1.创建一个服务对象,并在9090端口进行监听
        ServerSocket serverSocket=
                new ServerSocket(9090);
        System.out.println("server start ...");
        //2.开启服务监听(等待客户端的链接)
        while(flag){//思考:假如这个循环一直循环,会带来什么问题
            //2.1接收客户端的请求
            Socket socket =
            serverSocket.accept();//接收请求(没有请求时Thread.sleep(...))
            System.out.println("socket:"+socket);
            //2.2处理客户端的请求
            ObjectInputStream ois=
            new ObjectInputStream(socket.getInputStream());
            String msg=ois.readUTF();
            System.out.println("client say: "+msg);
        }

    }
}

package com.cy.net;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Java中的网络编程会涉及两部分内容
 * 1)API (ServerSocket-服务端,Socket-客户端)
 * 2)协议 (TCP,UDP)
 */

public class WebServer {//Nacos (8848)-application.properties
    static boolean flag=true;
    public static void main(String[] args) throws IOException {
         //1.创建一个服务对象,并在9090端口进行监听
        ServerSocket serverSocket=
                new ServerSocket(9091);
        System.out.println("server start ...");
        //2.开启服务监听(等待客户端的链接)
        while(flag){//思考:假如这个循环一直循环,会带来什么问题
            //2.1接收客户端的请求(accept为阻塞式方法)
            Socket socket = serverSocket.accept();
            //2.2处理客户端的请求
            OutputStream out=socket.getOutputStream();
            //向客户端写数据
            String content=
                    "HTTP/1.1 200\r\n" +//响应行
                    "Content-Type: text/html;charset=UTF-8\r\n"+//响应头
                    "\r\n"+//空行,
                    "<h1>hello client</h1>"; //数据
            out.write(content.getBytes());
        }
    }
}
//通过线程池中的线程处理请求?(使用池的目的,减少线程对象创建次数)

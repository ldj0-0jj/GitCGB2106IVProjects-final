package com.cy.net;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 网络中的一个客户端对象
 */
public class Browser {
    public static void main(String[] args) throws IOException {
        //1.构建客户端对象,并连接服务端
        Socket socket=
        new Socket("127.0.0.1",9090);
        //2.与服务端进行交互
        ObjectOutputStream out=
        new ObjectOutputStream(socket.getOutputStream());
        out.writeUTF("Browser");
        //3.释放资源
        out.close();
        socket.close();
    }
}

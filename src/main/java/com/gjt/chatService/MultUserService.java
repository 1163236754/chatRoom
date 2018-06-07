package com.gjt.chatService;

import com.gjt.chatService.entity.TCPEntity;

import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/24/20:14
 */
public class MultUserService {
    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(TCPEntity.port);
        /**
         * 记录当前链接数量
         */
        int count = 0;
        System.out.println("服务器启动，等待客户端的连接。。。");
        Socket socket = null;
        while (true){
            socket=serverSocket.accept();
            Thread thread = new Thread(new ChatServer(socket));
            thread.start();
            InetAddress inetAddress = socket.getInetAddress();
            System.out.println("当前客户端的IP地址是："+inetAddress.getHostAddress());
        }
    }
}

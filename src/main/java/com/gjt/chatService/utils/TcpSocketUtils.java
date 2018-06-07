package com.gjt.chatService.utils;

import com.gjt.chatService.entity.TCPEntity;

import java.io.IOException;
import java.net.Socket;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  tcpSocketClient
 * @date 2018/5/24/11:10
 */
public class TcpSocketUtils {
    /**
     * 建立socket连接
     * @return
     */
    public  Socket ConnectTcpClient(){
        Socket clientSocket = null;
        /**
         * 建立socket连接
         */
        try {
            clientSocket = new Socket(TCPEntity.conAddr, TCPEntity.port);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return clientSocket;
    }

    /**
     * 关闭连接
     * @param socket
     */
    public  void Close(Socket socket){
        try {
            socket.close();
            System.out.println("关闭成功！");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

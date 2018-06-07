package com.gjt.chatService;

import com.gjt.chatService.entity.TCPEntity;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/24/20:14
 */
public class MultUserService {

    private  ServerSocket serverSocket;
    /**
     * 创建线程池来管理客户端的连接线程
     * 避免系统资源过度浪费
     */
    private  ExecutorService exec;

    /**
     * 存放客户端之间私聊的信息
     */
    private static Map<String,PrintWriter> storeInfo;

    public MultUserService(){
        try {
            serverSocket = new ServerSocket(TCPEntity.port);
            storeInfo = new HashMap<>();
            exec = Executors.newCachedThreadPool();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void start() {
        try {
            System.out.println("等待客户端连接... ... ");
            Socket socket = serverSocket.accept();
            // 获取客户端的ip地址
            InetAddress address = socket.getInetAddress();
            System.out.println("客户端：“" + address.getHostAddress() + "”连接成功！ ");
            /*
             * 启动一个线程，由线程来处理客户端的请求，这样可以再次监听
             * 下一个客户端的连接
             */
            //通过线程池来分配线程
            exec.execute(new ChatServer(socket,storeInfo));
        } catch(Exception e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws Exception {
        MultUserService server = new MultUserService();
        server.start();
    }

}

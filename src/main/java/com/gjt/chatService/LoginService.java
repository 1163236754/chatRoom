package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.ResponseEntity;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/11/15:25
 */
public class LoginService{

    private ServerSocket serverSocket;

    private Socket socket;

    private ObjectInputStream reader;

    private ObjectOutputStream writer;

    // 存放线程的List
    private ArrayList<LoginThread> loginThreads;

    public LoginService(){
        try {
            serverSocket = new ServerSocket(6666);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Start(){
        ServiceThread serviceThread = new ServiceThread(serverSocket);
        serviceThread.start();
    }

    public static void main(String[] args) {
        LoginService loginService = new LoginService();
        loginService.Start();
    }

    class LoginThread extends Thread{

        private Socket socket;

        public LoginThread(Socket socket){
               this.socket = socket;
        }


        @Override
        public void run() {
            Object message = null;
            List<ResponseEntity> result = null;
            try {
                try {
                    reader = new ObjectInputStream(socket.getInputStream());
                    message =  reader.readObject();
                    if (message != null){
                        // 请求数据库
                        MainAction mainAction = new MainAction();
                        result = mainAction.DealWithAction(message);
                        String tips = "";
                        for (int i = 0; i < result.size(); i++) {
                            if(result.get(i).getResponseContent().equals("登陆成功")){
                                tips = "登陆成功";
                            }else if (result.get(i).getResponseContent().equals("登陆失败")){
                                tips = "登陆失败";
                            }
                        }
                        System.out.println("[系统通知：]"+tips);
                        if(tips.equals("登陆成功")){
                            MultUserService multUserService = new MultUserService();
                            multUserService.StartServerService();
                            // 你猜为什么要延迟。。哈哈哈，当然是为了给我时间让我来得及点启动
                            Thread.sleep(3000);
                        }else {
                            System.err.println("响应失败！请检查用户名和密码！");
                        }
                        writer = new ObjectOutputStream(socket.getOutputStream());
                        writer.writeObject(result);
                        writer.flush();
                    }
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    reader.close();
                    writer.close();
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }


    class ServiceThread extends Thread{

        private ServerSocket serverSocket;

        public ServiceThread(ServerSocket serverSocket){
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            try {
                socket = serverSocket.accept();
                LoginThread loginThread = new LoginThread(socket);
                loginThread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

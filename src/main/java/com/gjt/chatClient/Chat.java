package com.gjt.chatClient;

import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.utils.TcpSocketUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  聊天
 * @date 2018/6/1/16:50
 */
public class Chat{
    // 建立链接的工具
    private  TcpSocketUtils conn  = new TcpSocketUtils();
    // 写
    private ObjectOutputStream writer = null;
    // 读
    private ObjectInputStream reader = null;
    // 发送线程
    private Thread send = null;
    // 接收线程
    private Thread recive = null;
    // 登陆线程
    private Thread login = null;

    // 是否运行
    private boolean isRun = false;
    // socket链接
    private Socket connect;

    public void StartThread(String userName){
        connect = conn.ConnectTcpClient();
        // 登陆线程
        send = new Thread(new SendThread(connect,userName));
        send.start();
        isRun = true;
        // 接收线程
        recive =  new Thread(new ReceiveThread(connect));
        recive.start();
    }
    public static void main(String[] args) {
        Chat sendThread = new Chat();
        sendThread.StartThread("11621380111");

    }
    // 终止线程
    public synchronized boolean closeConnection() {
        try {
            // 这个方法有点暴力
            recive.stop();
            // 释放资源
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (connect != null) {
                connect.close();
            }
            isRun = false;
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isRun = true;
            return false;
        }
    }

    /**
     * 发送数据线程
     */
    class SendThread implements Runnable {
        private Socket client;
        /**
         * 消息实体封装
         */
        MessageEntity messageEntity = new MessageEntity();
        /**
         * 用户名
         */
        private String userName;


        public SendThread(Socket client, String userName) {
            this.client = client;
            this.userName = userName;
            try {
                writer = new ObjectOutputStream(client.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 发送消息
         */
        public void Send() {
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                if(messageEntity.getContent() != null){
                    messageEntity.setSenderIp(ip);
                    writer.writeObject(messageEntity);
                    writer.writeObject(null);
                    writer.flush();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return ;
        }

        public void SendMessage(){
//            System.out.println("这里发送消息");
//            messageEntity.setType("sendGroup");
//            messageEntity.setSender(userName);
//            messageEntity.setReciver("116213801");
//            messageEntity.setSenderTime(new Date());
//            messageEntity.setSenderName("王翰");
            // 单人测试
            System.out.println("这里发送消息");
            messageEntity.setType("send");
            messageEntity.setSender(userName);
            messageEntity.setReciver("11621380110");
            messageEntity.setSenderTime(new Date());
            messageEntity.setSenderName("黎明");
            Scanner sc = new Scanner(System.in);
            System.out.println("请输入你要输入的发送的");
            while (true){
                String content = sc.next();
                messageEntity.setContent(content);
                try {
                    Send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        @Override
        public void run() {
            while (isRun) {
                try {
                    System.out.println("发送线程打开");
                    SendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 消息接收
     * 每隔一段时间请求一次数据
     */
    class ReceiveThread implements Runnable {
        private Socket client;
        private boolean isRun = true;

        public ReceiveThread(Socket client) {
            this.client = client;
            try {
                reader = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 消息接收
         * @throws Exception
         */
        public void Recevice() throws Exception {
            Object message =  reader.readObject();
            List<ResponseEntity> responseEntities = (List<ResponseEntity>)message;
//            ReturnMessageEntity  returnMessageEntity = (ReturnMessageEntity)message;
            if(responseEntities != null){
                PrintMessage(responseEntities);
            }
            return;
        }

        /**
         * 打印消息
         * @param returnMessageEntity
         */
        private void PrintMessage(List<ResponseEntity> returnMessageEntity) {
            if(returnMessageEntity.get(0).getChatGroupmessages()!=null){
                for (int i = 0; i < returnMessageEntity.get(0).getChatGroupmessages().size(); i++) {
                    System.out.println("时间：" + returnMessageEntity.get(0).getChatGroupmessages().get(i).getTime().toString());
                    System.out.println("来自："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getName());
                    System.out.println("内容："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getContent());
                }
            }else {
                for (int i = 0; i < returnMessageEntity.get(0).getChatGroupmessages().size(); i++) {
                    System.out.println("时间：" + returnMessageEntity.get(0).getMessagesData().get(i).getSendTime().toString());
                    System.out.println("来自："+ returnMessageEntity.get(0).getMessagesData().get(i).getSender());
                    System.out.println("内容："+ returnMessageEntity.get(0).getMessagesData().get(i).getContent());
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void run() {
            while (isRun) {
                try {
                    Recevice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class ChatLoginClient implements Runnable{
        Socket connect = null;
        /**
         * 设置登陆信息
         */
        private  LoginEntity loginEntity = new LoginEntity();

        private String userName;

        private String password;

        public ChatLoginClient(Socket connect, String userName, String password) {
            this.connect = connect;
            try {
                writer = new ObjectOutputStream(connect.getOutputStream());
                reader=new ObjectInputStream(connect.getInputStream());
                this.userName = userName;
                this.password = password;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 发送登陆验证
         * @throws Exception
         */
        private  void SendMessage() throws Exception {
            writer.writeObject(loginEntity);
            writer.flush();
            List<ResponseEntity > obj = (List<ResponseEntity>) reader.readObject();
            String result = null;
            if(obj != null){
                for (int i = 0; i < obj.size(); i++) {
                    if(obj.get(i).getResponseContent().equals("登陆成功")){
                        result = "登陆成功";
                    }else if (obj.get(i).getResponseContent().equals("登陆失败")){
                        result = "登陆失败";
                    }
                }
                System.out.println("[系统通知：]"+result);
            }
            if(result.equals("登陆成功")){
                // 发送线程
                send = new Thread(new SendThread(connect,loginEntity.getUserName()));
                send.start();
                isRun = true;
                // 接收线程
                recive =  new Thread(new ReceiveThread(connect));
                recive.start();
                Thread.sleep(300);
                // 停止登陆进程
                login.stop();

            }else {
                System.out.println("登陆失败");
//                conn.Close(connect);
//                Login login = new Login();
            }

        }
        /**
         * 初始化登陆组装
         * @param userName
         * @param password
         */
        private  void initFiles(String userName,String password) {
            String ip = null;
            try {
                ip = InetAddress.getLocalHost().getHostAddress();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
            // 账号
            loginEntity.setUserName(userName);
            // 密码
            loginEntity.setPassword(password);
            // ip地址
            loginEntity.setIpAddress(ip);
            // 时间
            loginEntity.setLoginTime((new Date()).toString());
            System.out.println("***************用户基本信息***************");
            System.out.println("name:" + userName);
            System.out.println("password:" + password);
            System.out.println("登陆地址:" + ip);
            System.out.println("登陆时间:" + (new Date()).toString());
            System.out.println("***************用户基本信息***************");
        }
        @Override
        public void run() {
            initFiles(userName,password);
            try {
                SendMessage();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}





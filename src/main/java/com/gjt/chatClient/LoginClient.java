package com.gjt.chatClient;

import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.ResponseEntity;

import javax.swing.*;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/11/15:26
 */
public class LoginClient implements Runnable{

    Socket connect = null;
    /**
     * 设置登陆信息
     */
    private LoginEntity loginEntity = new LoginEntity();

    private String userName;

    private String password;

    private ObjectOutputStream writer;

    private ObjectInputStream reader;

    public LoginClient(Socket connect, String userName, String password) {
        this.connect = connect;
        try {
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

        writer = new ObjectOutputStream(connect.getOutputStream());
        writer.writeObject(loginEntity);
        writer.flush();
        reader=new ObjectInputStream(connect.getInputStream());
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
            writer.close();
            reader.close();
            connect.close();
            Thread.sleep(1000);
            Thread.yield();
            Chat chat = new Chat();
            chat.StartThread(userName);

        }else {
            System.out.println("登陆失败");
            JOptionPane.showMessageDialog(null, "登陆失败!",
                    "错误", JOptionPane.ERROR_MESSAGE);
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


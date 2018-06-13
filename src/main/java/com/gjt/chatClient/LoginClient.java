package com.gjt.chatClient;

import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.entity.TCPEntity;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
public class LoginClient  extends JFrame implements Runnable, ActionListener {

    /**
     * 设置登陆信息
     */
    private LoginEntity loginEntity = new LoginEntity();

    private String userName;

    private String password;

    private ObjectOutputStream writer;

    private ObjectInputStream reader;

    private JLabel userNameLabel;

    private JLabel passwordLabel;

    private JTextField userNameText;

    private JTextField passwordText;

    private JButton logIn;

    private Socket socket;

    private static Thread thread;

    public LoginClient() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        JPanel loPanel = new JPanel();
        loPanel.setLayout(null);
        loPanel.setBounds(0, 150, 600, 250);
        // 账号标签
        userNameLabel = new JLabel();
        userNameLabel.setText("账号：");
        userNameLabel.setBounds(100,20,60,30);
        loPanel.add(userNameLabel);
        // 密码标签
        passwordLabel = new JLabel();
        passwordLabel.setText("密码：");
        passwordLabel.setBounds(100,60,60,30);
        loPanel.add(passwordLabel);
        // 账号输入框
        userNameText = new JTextField();
        userNameText.setBounds(170,20,200,30);
        loPanel.add(userNameText);
        // 密码输入框
        passwordText = new JTextField();
        passwordText.setBounds(170,60,200,30);
        loPanel.add(passwordText);
        // 按钮注册
        logIn = new JButton();
        logIn.setText("登陆");
        logIn.setBounds(170,100,200,30);
        logIn.addActionListener(this);
        loPanel.add(logIn);
        try {
            socket = new Socket(TCPEntity.conAddr, 6666);
        } catch (Exception e) {
            System.err.println("链接失败");
            JOptionPane.showMessageDialog(null, "socket建立连接失败!",
                    "错误", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
        this.setTitle("QQ");
        this.setLayout(null);
        this.setBounds(400, 400, 500, 400);
        this.add(loPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }

    /**
     * 发送登陆验证
     * @throws Exception
     */
    private  void SendMessage() throws Exception {
        writer = new ObjectOutputStream(socket.getOutputStream());
        writer.writeObject(loginEntity);
        writer.flush();
        reader=new ObjectInputStream(socket.getInputStream());
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
            socket.close();
            try {
                dispose();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
            Chat chat = new Chat();
            chat.StartThread(userName,obj.get(0).getLoginMessage());

        }else {
            System.out.println("登陆失败");
            JOptionPane.showMessageDialog(null, "登陆失败!",
                    "错误", JOptionPane.ERROR_MESSAGE);
            SendMessage();
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

    @Override
    public void actionPerformed(ActionEvent e) {
        userName = userNameText.getText().trim();
        password = passwordText.getText().trim();
        String str = e.getActionCommand();
        if("登陆".equals(str)){
            // 开始登陆
            thread.start();
        }
    }

    public static void main(String[] args) {
         thread = new Thread(new LoginClient());

    }
}


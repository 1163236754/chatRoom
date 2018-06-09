package com.gjt.chatClient.ui.login;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/8/10:53
 */
public class LoginPanel extends JPanel implements ActionListener {

    private JLabel userNameLabel;

    private JLabel passwordLabel;

    private JTextField userNameText;

    private JTextField passwordText;

    private JButton logIn;

    private String userName;

    private String password;

    public LoginPanel(){
        this.setLayout(null);
        this.setBounds(0, 150, 600, 250);
        // 账号标签
        userNameLabel = new JLabel();
        userNameLabel.setText("账号：");
        userNameLabel.setBounds(100,20,60,30);
        this.add(userNameLabel);
        // 密码标签
        passwordLabel = new JLabel();
        passwordLabel.setText("密码：");
        passwordLabel.setBounds(100,60,60,30);
        this.add(passwordLabel);
        // 账号输入框
        userNameText = new JTextField();
        userNameText.setBounds(170,20,200,30);
        this.add(userNameText);
        // 密码输入框
        passwordText = new JTextField();
        passwordText.setBounds(170,60,200,30);
        this.add(passwordText);
        // 按钮注册
        logIn = new JButton();
        logIn.setText("登陆");
        logIn.setBounds(170,100,200,30);
        logIn.addActionListener(this);
        this.add(logIn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        userName = userNameText.getText().trim();
        password = passwordText.getText().trim();
        String str = e.getActionCommand();
        if("登陆".equals(str)){
//            ChatLoginClient chatLoginClient = new ChatLoginClient();
//            chatLoginClient.setLogin(userName,password);
//            try {
//                // 2s时间等待后台处理完成
//                Thread.sleep(2000);
//            } catch (InterruptedException e1) {
//                e1.printStackTrace();
//            }
        }
    }
}

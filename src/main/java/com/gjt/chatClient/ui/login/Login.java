package com.gjt.chatClient.ui.login;

import javax.swing.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  登陆界面
 * @date 2018/6/8/10:47
 */
public class Login extends JFrame {

    public Login(){
        LoginPanel loPanel = new LoginPanel();
        HeadPanel headPanel = new HeadPanel();
        this.setTitle("GIM");
        this.setLayout(null);
        this.setBounds(400, 400, 500, 400);
        this.add(headPanel);
        this.add(loPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        Login login = new Login();
    }

}

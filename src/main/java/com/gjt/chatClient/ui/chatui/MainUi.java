package com.gjt.chatClient.ui.chatui;

import javax.swing.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 主窗体
 * @date 2018/6/8/11:43
 */
public class MainUi extends JFrame{

    public MainUi(){
        ListPanel listPanel = new ListPanel();
        this.setTitle("GIM");
        this.setLayout(null);
        this.setBounds(700, 200, 900, 700);
        this.add(listPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    public static void main(String[] args) {
        MainUi mainUi = new MainUi();
    }
}

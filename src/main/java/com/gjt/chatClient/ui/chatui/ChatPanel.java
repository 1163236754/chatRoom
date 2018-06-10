//package com.gjt.chatClient.ui.chatui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//
///**
// * @author 官江涛
// * @version 1.0
// * @Title:  聊天窗口
// * @date 2018/6/8/11:46
// */
//public class ChatPanel extends JPanel implements ActionListener, KeyListener {
//    // 发送
//    private JButton submit;
//    // 输入框
//    private JTextField inputField;
//    // 文本域
//    private JTextArea contentArea;
//    // 显示当前在和谁聊天
//    private JLabel jLabel;
//
//
//
//    public ChatPanel(){
//        this.setBounds(200,0,700,700);
//        this.setLayout(null);
//        submit = new JButton();
//        submit.setBounds(600,600,90,50);
//        submit.setText("发送");
//        submit.addActionListener(this);
//        this.add(submit);
//        inputField = new JTextField();
//        inputField.setBounds(10,600,580,50);
//        this.add(inputField);
//        inputField.addKeyListener(this);
//        contentArea = new JTextArea();
//        contentArea.setBounds(10,30,680,540);
//        contentArea.setFont(new   java.awt.Font("Dialog",   1,   14));
//        jLabel = new JLabel();
//        jLabel.setBackground(Color.gray);
//        jLabel.setText("这是个测试label");
//        jLabel.setFont(new   java.awt.Font("Dialog",   1,   17));
//        jLabel.setBounds(10,0,680,30);
//        this.add(contentArea);
//        this.add(jLabel);
//
//    }
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String str = e.getActionCommand();
//        String inputText = inputField.getText().trim();
//        if(str.equals("发送")){
//            contentArea.append("   "+inputText+"\n");
//            // 清空消息
//            inputField.setText(null);
//        }
//    }
//
//    @Override
//    public void keyTyped(KeyEvent e) { }
//
//    @Override
//    public void keyPressed(KeyEvent e) {
//        if(e.getKeyChar()==KeyEvent.VK_ENTER )
//        {
//            submit.doClick();
//        }
//    }
//
//    @Override
//    public void keyReleased(KeyEvent e) { }
//}

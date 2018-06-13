package com.gjt.chatClient.ui.login;

import javax.swing.*;
import java.awt.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 头部
 * @date 2018/6/8/10:55
 */
public class HeadPanel extends JPanel {

    public HeadPanel(){
        this.setLayout(null);
        this.setBounds(0,0,600,200);
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        super.paintComponent(g);
        //绘制一张背景图片  2.jpg是图片的路径  自己设定为自己想要添加的图片
        Image image = new ImageIcon("/com/gjt/chatClient/ui/login/image/header.png").getImage();
        g.drawImage(image, 0, 0, this);
    }
}
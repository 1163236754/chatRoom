package com.gjt.chatClient.ui.login;

import javax.swing.*;
import java.awt.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 头部
 * @date 2018/6/8/10:55
 */
public class HeadPanel extends JPanel{

    ImageIcon icon;
    Image img;

    public HeadPanel(){
        //  /img/HomeImg.jpg 是存放在你正在编写的项目的bin文件夹下的img文件夹下的一个图片
        icon=new ImageIcon(getClass().getResource("/image/header.png"));
        img=icon.getImage();
    }
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //下面这行是为了背景图片可以跟随窗口自行调整大小，可以自己设置成固定大小
        g.drawImage(img, 0, 0,this.getWidth(), this.getHeight(), this);
    }
}

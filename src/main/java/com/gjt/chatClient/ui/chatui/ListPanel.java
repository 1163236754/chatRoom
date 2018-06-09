package com.gjt.chatClient.ui.chatui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  列表窗口
 * @date 2018/6/8/11:45
 */
public class ListPanel extends JPanel implements ActionListener {
    /**
     * 好友列表
     */
    private JButton friendList;
    /**
     * 分组列表
     */
    private JButton groupList;
    /**
     * 全局列表
     */
    String[] list;

    public ListPanel(){
        this.setBounds(0,0,200,700);
        this.setLayout(null);
        this.setBackground(Color.red);
        // 好友列表
        friendList = new JButton();
        friendList.setText("好友列表");
        // 让按钮上的字完全显示
        friendList.setMargin(new java.awt.Insets(0,0,0,0));
        friendList.setBounds(10,20,80,30);
        friendList.addActionListener(this);
        this.add(friendList);
        // 群消息列表
        groupList = new JButton();
        groupList.setText("群消息列表");
        // 让按钮上的字完全显示
        groupList.setMargin(new java.awt.Insets(0,0,0,0));
        groupList.setBounds(100,20,80,30);
        groupList.addActionListener(this);
        this.add(groupList);
        // 展示好友列表
        list = new String[]{"香蕉", "雪梨", "苹果", "荔枝"};
        this.add(SetJList());

    }

    /**
     * 设置Jlist
     * @return
     */
    private JList<String> SetJList(){
        JList<String> jList = new JList<>();
        jList.setBounds(10,60,180,620);
        // 设置一下首选大小
        jList.setPreferredSize(new Dimension(200, 100));

        // 允许可间断的多选
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);

        // 设置选项数据（内部将自动封装成 ListModel ）
        jList.setListData(list);

        // 添加选项选中状态被改变的监听器
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
                int[] indices = jList.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = jList.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                }
                System.out.println();
            }
        });
        return jList;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        if(str.equals("好友列表")){
            System.out.println(str);
            remove(SetJList());
            list = new String[]{"好友一", "好友二", "好友三", "好友四"};
            repaint();
            SetJList();
            this.add(SetJList());
        }else {
            remove(SetJList());
            list = new String[]{"测试群一", "测试群二", "测试群三", "测试群四"};
            System.out.println(str);
            repaint();
            SetJList();
            this.add(SetJList());
        }
    }
}

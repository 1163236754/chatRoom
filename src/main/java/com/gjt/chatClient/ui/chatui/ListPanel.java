//package com.gjt.chatClient.ui.chatui;
//
//import javax.swing.*;
//import javax.swing.event.ListSelectionEvent;
//import javax.swing.event.ListSelectionListener;
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//
///**
// * @author 官江涛
// * @version 1.0
// * @Title:  列表窗口
// * @date 2018/6/8/11:45
// */
//public class ListPanel extends JPanel implements ActionListener {
//    /**
//     * 好友列表
//     */
//    private JButton friendList;
//    /**
//     * 分组列表
//     */
//    private JButton groupList;
//    /**
//     * listModel用于动态更新list数据
//     */
//    private DefaultListModel listModel;
//    /**
//     * 列表
//     */
//    private JList jList;
//    /**
//     * 全局列表 好友
//     */
//    String[] list1;
//    /**
//     * 全局列表 群
//     */
//    String[] list2;
//    /**
//     * 点击的名称
//     */
//    String clickName;
//    /**
//     * 展示标签
//     */
//    JLabel jLabel;
//
//    public ListPanel( ){
//        this.setBounds(0,0,200,700);
//        this.setLayout(null);
//        // 好友列表
//        friendList = new JButton();
//        friendList.setText("好友列表");
//        // 让按钮上的字完全显示
//        friendList.setMargin(new java.awt.Insets(0,0,0,0));
//        friendList.setBounds(10,20,80,30);
//        friendList.addActionListener(this);
//        this.add(friendList);
//        // 群消息列表
//        groupList = new JButton();
//        groupList.setText("群消息列表");
//        // 让按钮上的字完全显示
//        groupList.setMargin(new java.awt.Insets(0,0,0,0));
//        groupList.setBounds(100,20,80,30);
//        groupList.addActionListener(this);
//        this.add(groupList);
//        // 展示好友列表
//        listModel = new DefaultListModel();
//        jList = new JList(listModel);
//        jList.setBounds(10,60,180,620);
//        // 设置一下首选大小
//        jList.setPreferredSize(new Dimension(200, 100));
//
//        // 允许可间断的多选
//        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
//        jList.addListSelectionListener(new ListSelectionListener() {
//            @Override
//            public void valueChanged(ListSelectionEvent e) {
//                // 获取所有被选中的选项索引
//                int[] indices = jList.getSelectedIndices();
//                // 获取选项数据的 ListModel
//                ListModel<String> listModel = jList.getModel();
//                // 输出选中的选项
//                for (int index : indices) {
//                    clickName = listModel.getElementAt(index);
//                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
//                }
//                System.out.println();
//            }
//        });
//        this.add(jList);
//    }
//
//    @Override
//    public void actionPerformed(ActionEvent e) {
//        String str = e.getActionCommand();
//        if(str.equals("好友列表")){
//            System.out.println(str);
//            list1 = new String[]{"好友一", "好友二", "好友三", "好友四"};
//            listModel.removeAllElements();
//            for (int i = 0; i < list1.length; i++) {
//                listModel.addElement(list1[i]);
//            }
//        }else {
//            list2 = new String[]{"测试群一", "测试群二", "测试群三", "测试群四"};
//            listModel.removeAllElements();
//            for (int i = 0; i < list2.length; i++) {
//                listModel.addElement(list2[i]);
//            }
//        }
//    }
//}

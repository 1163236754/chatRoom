package com.gjt.chatClient;

//import com.gjt.chatClient.ui.chatui.ChatPanel;

import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.entity.UserEntity;
import com.gjt.chatService.utils.TcpSocketUtils;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  聊天
 * @date 2018/6/1/16:50
 */
public class Chat extends JFrame implements ActionListener {
    // 建立链接的工具
    private  TcpSocketUtils conn  = new TcpSocketUtils();
    // 写
    private ObjectOutputStream writer = null;
    // 读
    private ObjectInputStream reader = null;
    // 发送线程
    private Thread send = null;
    // 接收线程
    private Thread recive = null;
    // 登陆线程
    private Thread login = null;

    // 是否运行
    private boolean isRun = false;
    // socket链接
    private Socket connect;
    // 好友列表
    private JButton friendList;
    // 分组列表
    private JButton groupList;
    // listModel用于动态更新list数据
    private DefaultListModel listModel;
    // 列表
    private JList jList;
    // 全局列表 好友
    private String[] friend;
    // 全局列表 群
    private String[] group;
    // 点击的名称
    private  String clickName;
    // 发送
    private JButton submit;
    // 输入框
    private JTextField inputField;
    // 文本域
    private JTextArea contentArea;
    // 显示当前在和谁聊天
    private JLabel jLabel;

    private JPanel listPanel;
    private JPanel chatPanel;

    private JScrollPane sroll;
    // 消息实体封装
    private MessageEntity messageEntity ;
    // 发送人
    private String userName;
    //  接收人
    private String revicerName;
    //  类型
    private String type = "send";
    // 目标标题
    private String targetTitle;
    // 目标标题
    private String senderName;
    // 头部
    private JPanel header;
    // 新增标签
    private JLabel addTips;
    // 新增的Id
    private JTextField addId;
    // 新增按钮
    private JButton addNewFriend;
    // 查看消息记录
    private JButton msglist;
    // 好友列表
    private List<UserEntity> userEntityList;
    // 聊天记录
    private List<ChatMessage> chatMessages;

    private JScrollPane scroll;

    public Chat(){
        // 左边的panel
        listPanel = new JPanel();
        listPanel.setBounds(0,100,200,500);
        listPanel.setLayout(null);
        // 好友列表
        friendList = new JButton();
        friendList.setText("好友列表");
        // 让按钮上的字完全显示
        friendList.setMargin(new java.awt.Insets(0,0,0,0));
        friendList.setBounds(10,20,80,30);
        friendList.addActionListener(this);
        listPanel.add(friendList);
        // 群消息列表
        groupList = new JButton();
        groupList.setText("群列表");
        // 让按钮上的字完全显示
        groupList.setMargin(new java.awt.Insets(0,0,0,0));
        groupList.setBounds(100,20,80,30);
        groupList.addActionListener(this);
        listPanel.add(groupList);
        // 展示好友列表
        listModel = new DefaultListModel();
        friend = new String[]{"好友一", "好友二", "好友三", "好友四"};
        for (int i = 0; i < friend.length; i++) {
            listModel.addElement(friend[i]);
        }
        jList = new JList(listModel);
        jList.setBounds(10,60,180,620);
        // 设置一下首选大小
        jList.setPreferredSize(new Dimension(200, 100));

        // 允许可间断的多选
        jList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // 获取所有被选中的选项索引
                int[] indices = jList.getSelectedIndices();
                // 获取选项数据的 ListModel
                ListModel<String> listModel = jList.getModel();
                // 输出选中的选项
                for (int index : indices) {
                    revicerName = listModel.getElementAt(index);
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                }
                System.out.println();
            }
        });
        listPanel.add(jList);
        // 右边的
        chatPanel = new JPanel();
        chatPanel.setBounds(200,110,700,600);
        chatPanel.setLayout(null);
        submit = new JButton();
        submit.setBounds(600,460,90,50);
        submit.setText("发送");
        chatPanel.add(submit);
        inputField = new JTextField();
        inputField.setBounds(10,460,580,50);
        chatPanel.add(inputField);
        // 键盘监听事件
        contentArea = new JTextArea();
        contentArea.setBounds(10,30,670,400);
        contentArea.setFont(new   java.awt.Font("Dialog",   1,   14));
        contentArea.setBorder(new TitledBorder("消息显示区"));

        scroll = new JScrollPane();
        scroll.add(contentArea);
        scroll.setViewportView(contentArea);
        scroll.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroll.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scroll.setPreferredSize(new Dimension(800,400));
        scroll.validate();
        scroll.setBounds(10,30,670,400);
        jLabel = new JLabel();
        jLabel.setBackground(Color.gray);
//        jLabel.setText("这是个测试label");
        jLabel.setFont(new   java.awt.Font("Dialog",   1,   17));
        jLabel.setBounds(10,0,680,30);
        chatPanel.add(scroll);
//        chatPanel.add(contentArea);
        chatPanel.add(jLabel);
        // 头部
        header = new JPanel();
        header.setBounds(0,10,900,100);
        header.setLayout(null);
        header.setBackground(Color.WHITE);
        addTips = new JLabel();
        addTips.setText("请输入ID:");
        addTips.setFont(new   java.awt.Font("Dialog",   1,   15));
        addTips.setBounds(50,30,100,30);
        addId = new JTextField();
        addId.setBounds(160,30,200,30);
        addNewFriend = new JButton();
        addNewFriend.setText("新增");
        addNewFriend.setBounds(370,30,100,30);
        msglist = new JButton();
        msglist.setText("查看消息历史记录");
        msglist.setBounds(480,30,150,30);
        header.add(addTips);
        header.add(addId);
        header.add(addNewFriend);
        header.add(msglist);
        header.setBorder(new TitledBorder("用户操作"));
        this.setLayout(null);
        this.setBounds(700, 200, 900, 660);
        this.setBackground(new Color(238,238,238));
        this.add(header);
        this.add(listPanel);
        this.add(chatPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        connect = conn.ConnectTcpClient();
    }
    public void StartThread(String userName, Map<String, Object> loginMessage){
        // 完成初始消息封装
        this.userName = userName;
        senderName = loginMessage.get("name").toString();
        this.setTitle("GIM 当前账号："+senderName);
        System.out.println("发送人名称："+senderName);
        send =  new Thread(new SendThread(inputField,submit,addNewFriend,msglist,jLabel));
        send.start();
        isRun = true;
        // 接收线程
        recive =  new Thread(new ReceiveThread(connect));
        recive.start();
    }
    // 终止线程
    public synchronized boolean closeConnection() {
        try {
            // 这个方法有点暴力
            recive.stop();
            // 释放资源
            if (reader != null) {
                reader.close();
            }
            if (writer != null) {
                writer.close();
            }
            if (connect != null) {
                connect.close();
            }
            isRun = false;
            return true;
        } catch (IOException e1) {
            e1.printStackTrace();
            isRun = true;
            return false;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String str = e.getActionCommand();
        String inputText = inputField.getText().trim();
        if(str.equals("好友列表")){
            System.out.println(str);
            GetFriendInList();
            type = "send";
            contentArea.setText(null);

        }else if (str.equals("群列表")){
            contentArea.setText(null);
            type = "sendGroup";
            revicerName = userName.substring(0,9);
            System.out.println("设置群名称：" + revicerName);
            group = new String[]{revicerName};
            listModel.removeAllElements();
            for (int i = 0; i < group.length; i++) {
                listModel.addElement(group[i]);
            }
        }
    }
    /**
     *  获取好友列表
     */
    private void GetFriendInList(){
        friend = new String[userEntityList.size()];
        for (int i = 0; i < userEntityList.size(); i++) {
            friend[i] = userEntityList.get(i).getUsername();
        }
        listModel.removeAllElements();
        for (int i = 0; i < friend.length; i++) {
            listModel.addElement(friend[i]);
        }
//        return friend;
    }
    class SendThread implements Runnable{
        // 输入框
        private JTextField inputField;
        // 按钮发送按钮
        private JButton submit;
        // 新增增好友
        private JButton addNewFriend;
        // 查看历史消息
        private JButton msglist;
        // 标签
        private JLabel jLabe;
        public SendThread(JTextField inputField,  JButton submit, JButton addNewFriend, JButton msglist,JLabel jLabel) {
            messageEntity = new MessageEntity();
            // 暂时只做显示账号
            this.inputField = inputField;
            this.submit = submit;
            this.msglist = msglist;
            this.addNewFriend = addNewFriend;
            this.jLabe = jLabel;
            jLabel.setText(revicerName);
            inputField.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) { }
                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyChar()==KeyEvent.VK_ENTER ) { submit.doClick(); } }
                @Override
                public void keyReleased(KeyEvent e) { }
            });
            submit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String content = inputField.getText();
                    SendMessage(content);
                    inputField.setText(null);
                }
            });
            addNewFriend.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // 设置类型为新增好友
                    type = "addFriend";
                    // 设置接收人为要加的好友
                    revicerName = addId.getText().trim();
                    String content = "添加好友";
                    SendMessage(content);
                }
            });
            msglist.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String str =  e.getActionCommand();
                    if(str.equals("查看消息历史记录")){
                        // 设置类型为新增好友
                        type = "request";
                        // 设置接收人为自己
                        revicerName = "@";
                        String content = "请求消息";
                        SendMessage(content);
                    }
                }
            });
        }
        /**
         * 发送消息
         */
        public void Send() {
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                try {
                    writer = new ObjectOutputStream(connect.getOutputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(messageEntity.getContent() != null){
                    messageEntity.setSenderIp(ip);
                    writer.writeObject(messageEntity);
                    writer.flush();
                }
            }catch (IOException e) {
                e.printStackTrace();
            }
            return ;
        }
        /**
         * 用户输入框
         */
        public void SendMessage(String content){
            isRun = true;
            messageEntity.setType(type);
            messageEntity.setSender(userName);
            messageEntity.setReciver(revicerName);
            messageEntity.setSenderTime(new Date());
            messageEntity.setSenderName(senderName);
            if(content!=null){
                messageEntity.setContent(content);
                Send();
            }
        }
        /**
         * 建立链接
         */
        private void connected(){
            if(isRun){
                messageEntity.setType("requestFriendList");
                messageEntity.setSender(userName);
                messageEntity.setReciver(revicerName);
                messageEntity.setSenderTime(new Date());
                messageEntity.setSenderName(senderName);
                messageEntity.setContent("请求好友列表");
                Send();
            }else {
                isRun = false;
                System.out.println("请求失败");
                connected();
            }
            return ;
        }
        @Override
        public void run() {
            System.out.println("发送线程启动");
            connected();
//            while (isRun){
//
//                try {
//                    Thread.sleep(5000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

    class ReceiveThread implements Runnable {
        private Socket client;

        public ReceiveThread(Socket client) {
            this.client = client;
            try {
                reader = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public ObjectInputStream getObjectInputStream(){
            return reader;
        }
        /**
         * 消息接收
         * @throws Exception
         */
        public void Recevice() throws Exception {
            List<ResponseEntity> responseEntities = (List<ResponseEntity>)getObjectInputStream().readObject();
            if(responseEntities != null){
                if(responseEntities.get(0).getType() != null){
                    switch (responseEntities.get(0).getType()){
                        case "addFriend":
                            JOptionPane.showMessageDialog(null, "添加成功!");break;
                        case "requestFriendList":
                            userEntityList = responseEntities.get(0).getUserEntities();
                            GetFriendInList();break;
                        case "request":
                            SaveMessage(responseEntities);break;
                        default:
                            System.out.println("指令错误");

                    }
                }else {
                    PrintMessage(responseEntities);
                }
            }
            return;

        }
        /**
         * 打印消息
         * @param returnMessageEntity
         */
        private void PrintMessage(List<ResponseEntity> returnMessageEntity) {
            if(returnMessageEntity.get(0).getChatGroupmessages()!=null){
                for (int i = 0; i < returnMessageEntity.get(0).getChatGroupmessages().size(); i++) {
                    contentArea.append(" 时间" + returnMessageEntity.get(0).getChatGroupmessages().get(i).getTime().toString()+"\n");
                    contentArea.append(" 来自："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getName()+"\n");
                    contentArea.append(" 内容："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getContent()+"\n");
                    System.out.println("时间" + returnMessageEntity.get(0).getChatGroupmessages().get(i).getTime().toString());
                    System.out.println("来自："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getName());
                    System.out.println("内容："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getContent());
                }
            }else {
                for (int i = 0; i < returnMessageEntity.get(0).getMessagesData().size(); i++) {
                    contentArea.append(" 时间：" + returnMessageEntity.get(0).getMessagesData().get(i).getSendTime().toString()+"\n");
                    contentArea.append(" 来自："+ returnMessageEntity.get(0).getMessagesData().get(i).getSender()+"\n");
                    contentArea.append(" 内容："+ returnMessageEntity.get(0).getMessagesData().get(i).getContent()+"\n");
                    System.out.println("时间：" + returnMessageEntity.get(0).getMessagesData().get(i).getSendTime().toString());
                    System.out.println("来自："+ returnMessageEntity.get(0).getMessagesData().get(i).getSender());
                    System.out.println("内容："+ returnMessageEntity.get(0).getMessagesData().get(i).getContent());
                }
            }


            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /**
         * 打印并保存消息
         * @param responseEntities
         */
        private void SaveMessage(List<ResponseEntity> responseEntities) throws IOException {
            File file = new File("Msg"+userName+".txt");
            FileWriter out =  new FileWriter(file);;
            for (int i = 0; i < responseEntities.get(0).getMessagesData().size(); i++) {
                System.out.println("时间：" + responseEntities.get(0).getMessagesData().get(i).getSendTime().toString());
                System.out.println("来自："+ responseEntities.get(0).getMessagesData().get(i).getSender());
                System.out.println("内容："+ responseEntities.get(0).getMessagesData().get(i).getContent());
                String msg =  "时间：" + responseEntities.get(0).getMessagesData().get(i).getSendTime().toString()+"\n"+"来自："
                        + responseEntities.get(0).getMessagesData().get(i).getSender()+"\n"+
                        "内容："+ responseEntities.get(0).getMessagesData().get(i).getContent()+"\n";
                try{
                    contentArea.append(msg);
                }catch (Exception e){
                    e.printStackTrace();
                }
                try {
                    out.write(msg+"\n");
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            out.close();
            JOptionPane.showMessageDialog(null, "已获取到聊天消息!");
            try{
                Runtime.getRuntime().exec("cmd /c start notepad .\\"+"Msg"+userName+".txt");
            }catch(Exception e2)
            {e2.printStackTrace();
            }
        }


        @Override
        public void run() {
            while (isRun) {
                try {
                    Recevice();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Chat chat = new Chat();
    }
}
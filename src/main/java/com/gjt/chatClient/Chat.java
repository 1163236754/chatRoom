package com.gjt.chatClient;

//import com.gjt.chatClient.ui.chatui.ChatPanel;
import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.utils.TcpSocketUtils;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
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
 * @Title:  聊天
 * @date 2018/6/1/16:50
 */
public class Chat extends JFrame implements ActionListener, KeyListener {
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
    private String[] list1;
    // 全局列表 群
    private String[] list2;
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

    static private Chat chat;

    public Chat(){
        listPanel = new JPanel();
        listPanel.setBounds(0,0,200,700);
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
        groupList.setText("群消息列表");
        // 让按钮上的字完全显示
        groupList.setMargin(new java.awt.Insets(0,0,0,0));
        groupList.setBounds(100,20,80,30);
        groupList.addActionListener(this);
        listPanel.add(groupList);
        // 展示好友列表
        listModel = new DefaultListModel();
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
                    clickName = listModel.getElementAt(index);
                    System.out.println("选中: " + index + " = " + listModel.getElementAt(index));
                }
                System.out.println();
            }
        });
        listPanel.add(jList);

        chatPanel = new JPanel();
        chatPanel.setBounds(200,0,700,700);
        chatPanel.setLayout(null);
        submit = new JButton();
        submit.setBounds(600,600,90,50);
        submit.setText("发送");
        submit.addActionListener(this);
        chatPanel.add(submit);
        inputField = new JTextField();
        inputField.setBounds(10,600,580,50);
        chatPanel.add(inputField);
        inputField.addKeyListener(this);
        contentArea = new JTextArea();
        contentArea.setBounds(10,30,680,540);
        contentArea.setFont(new   java.awt.Font("Dialog",   1,   14));
        jLabel = new JLabel();
        jLabel.setBackground(Color.gray);
        jLabel.setText("这是个测试label");
        jLabel.setFont(new   java.awt.Font("Dialog",   1,   17));
        jLabel.setBounds(10,0,680,30);
        chatPanel.add(contentArea);
        chatPanel.add(jLabel);
        this.setTitle("GIM");
        this.setLayout(null);
        this.setBounds(700, 200, 900, 700);
        this.setBackground(new Color(238,238,238));
        this.add(listPanel);
        this.add(chatPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);

    }


    public void StartThread(String userName){
        connect = conn.ConnectTcpClient();
        // 登陆线程
        send = new Thread(new SendThread(connect,userName));
        send.start();
        isRun = true;
        // 接收线程
        recive =  new Thread(new ReceiveThread(connect));
        recive.start();
    }
    public static void main(String[] args) {
        chat = new Chat();
        chat.StartThread("11621380110");
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
            list1 = new String[]{"好友一", "好友二", "好友三", "好友四"};
            listModel.removeAllElements();
            for (int i = 0; i < list1.length; i++) {
                listModel.addElement(list1[i]);
            }
        }else if (str.equals("好友列表")){
            list2 = new String[]{"测试群一", "测试群二", "测试群三", "测试群四"};
            listModel.removeAllElements();
            for (int i = 0; i < list2.length; i++) {
                listModel.addElement(list2[i]);
            }
        }else if(str.equals("发送")){
            contentArea.append(" "+"11621380110"+"：   "+inputText+"\n");
            // 清空消息
            inputField.setText(null);
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyChar()==KeyEvent.VK_ENTER )
        {
            submit.doClick();
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    /**
     * 发送数据线程
     */
    public class SendThread implements Runnable {
        private Socket client;
        /**
         * 消息实体封装
         */
        MessageEntity messageEntity = new MessageEntity();
        /**
         * 用户名
         */
        private String userName;
        /**
         * 是否启动
         */
        private Boolean isStart = false;

        public SendThread(Socket client, String userName) {
            this.client = client;
            this.userName = userName;

        }

        /**
         * 发送消息
         */
        public void Send() {
            try {
                String ip = InetAddress.getLocalHost().getHostAddress();
                try {
                    writer = new ObjectOutputStream(client.getOutputStream());
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

//        private void PrintMessage(MessageEntity messageEntity) {
//            System.out.println("我：");
//            System.out.println("时间：" + messageEntity.getSenderTime());
//            System.out.println("来自："+messageEntity.getSenderName());
//            System.out.println("内容："+messageEntity.getContent());
//            try {
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return ;
//        }

        /**
         * 建立链接
         */
        private boolean connected(){
            if(!isStart){
                messageEntity.setType("conn");
                messageEntity.setSender(userName);
                messageEntity.setReciver("11621380110");
                messageEntity.setSenderTime(new Date());
//                messageEntity.setSenderName("官江涛");
                messageEntity.setSenderName("黎明");
                messageEntity.setContent("建立链接");
                Send();
                isStart = true;
                System.out.println("链接成功");
            }else {
                isStart = false;
                System.out.println("链接失败,重新建立链接");
                connected();
            }
            return isStart;
        }
        public void SendMessage(){
//            System.out.println("这里发送消息");
//            messageEntity.setType("sendGroup");
//            messageEntity.setSender(userName);
//            messageEntity.setReciver("116213801");
//            messageEntity.setSenderTime(new Date());
//            messageEntity.setSenderName("王翰");
            // 单人测试
//            System.out.println("这里发送消息");
            messageEntity.setType("send");
            messageEntity.setSender(userName);
            messageEntity.setReciver("11621380110");
            messageEntity.setSenderTime(new Date());
            messageEntity.setSenderName("测试2");
//            Scanner sc = new Scanner(System.in);
//            System.out.println("请输入你要输入的发送的");
            String content = inputField.getText();
            if(content!=null){
                messageEntity.setContent(content);
                Send();
            }
//            while (true){
////                String content = sc.next();
//
//                try {
//                    messageEntity.setContent(content);
//                    Send();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
        }
        @Override
        public void run() {
            while (connected()) {
                try {
                    System.out.println("发送线程打开");
                    SendMessage();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    /**
     * 消息接收
     * 每隔一段时间请求一次数据
     */
    public class ReceiveThread implements Runnable {
        private Socket client;
        private boolean isRun = true;

        public ReceiveThread(Socket client) {
            this.client = client;
            try {
                reader = new ObjectInputStream(client.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 消息接收
         * @throws Exception
         */
        public void Recevice() throws Exception {
            Object message =  reader.readObject();
            List<ResponseEntity> responseEntities = (List<ResponseEntity>)message;
//            ReturnMessageEntity  returnMessageEntity = (ReturnMessageEntity)message;
            if(responseEntities != null){
                PrintMessage(responseEntities);
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
                    System.out.println("\t\t\t时间" + returnMessageEntity.get(0).getChatGroupmessages().get(i).getTime().toString());
                    System.out.println("来自："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getName());
                    System.out.println("内容："+ returnMessageEntity.get(0).getChatGroupmessages().get(i).getContent());
                }
            }else {
                for (int i = 0; i < returnMessageEntity.get(0).getChatGroupmessages().size(); i++) {
                    System.out.println("\t\t\t时间：" + returnMessageEntity.get(0).getMessagesData().get(i).getSendTime().toString());
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

    public class ChatLoginClient implements Runnable{
        Socket connect = null;
        /**
         * 设置登陆信息
         */
        private  LoginEntity loginEntity = new LoginEntity();

        private String userName;

        private String password;

        public ChatLoginClient(Socket connect, String userName, String password) {
            this.connect = connect;
            try {
                writer = new ObjectOutputStream(connect.getOutputStream());
                reader=new ObjectInputStream(connect.getInputStream());
                this.userName = userName;
                this.password = password;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        /**
         * 发送登陆验证
         * @throws Exception
         */
        private  void SendMessage() throws Exception {
            writer.writeObject(loginEntity);
            writer.flush();
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
                // 发送线程
                send = new Thread(new SendThread(connect,loginEntity.getUserName()));
                send.start();
                isRun = true;
                // 接收线程
                recive =  new Thread(new ReceiveThread(connect));
                recive.start();
                Thread.sleep(300);
                // 停止登陆进程
                login.stop();

            }else {
                System.out.println("登陆失败");
//                conn.Close(connect);
//                Login login = new Login();
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
    }
}





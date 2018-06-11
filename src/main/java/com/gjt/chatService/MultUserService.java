package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.*;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/24/20:14
 */
public class MultUserService extends JFrame{
    // 服务器socket
    private ServerSocket serverSocket;
    // 存放线程的List
    private ArrayList<ChatServer> clients;
    // 存放线程的 key—value
    private Map<String,ChatServer> clientMap;
    // 服务器线程
    private ServerThread serverThread;
    // 返回消息封装
    private List<ResponseEntity> returnMessageEntities;
    // 消息类型提取
    private Map<String,String> type;
    // 是否启动
    private Boolean isStart = false;
    //左边滚动条
    private JScrollPane rightPanel;
    //右边滚动条
    private JScrollPane leftPanel;
    //分割线
    private JSplitPane centerSplit;
    //列表组件
    private JList userList;
    private DefaultListModel listModel;
    //北方面板
    private JPanel northPanel;
    //南方面板
    private JPanel southPanel;
    //文本域
    private JTextArea contentArea;
    //开始按钮
    private JButton btn_start;
    //断开按钮
    private JButton btn_stop;
    //用于显示文本信息
    private JLabel txt_message;
    // 在线人数标签
    private JLabel onlineNumLabel;
    // 在线人数
    private int onlineNum;


    public MultUserService() {
        // 设置文本域
        contentArea = new JTextArea();
        contentArea.setEditable(false);
        contentArea.setForeground(Color.blue);
        // 设置服务器启动按钮
        btn_start = new JButton("启动");
        // 设置服务器停止按钮
        btn_stop = new JButton("停止");
        btn_stop.setEnabled(false);
        listModel = new DefaultListModel();
        userList = new JList(listModel);
        southPanel = new JPanel(new BorderLayout());
        leftPanel = new JScrollPane(userList);
        leftPanel.setBorder(new TitledBorder("在线用户"));

        rightPanel = new JScrollPane(contentArea);
        rightPanel.setBorder(new TitledBorder("消息显示区"));

        centerSplit = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel,
                rightPanel);
        centerSplit.setDividerLocation(100);
        northPanel = new JPanel();
        northPanel.setLayout(new GridLayout(1, 6));
        txt_message = new JLabel();
        txt_message.setText("你好，管理员！");
        onlineNumLabel = new JLabel();
        onlineNumLabel.setText("当前在线人数："+onlineNum);
        northPanel.add(onlineNumLabel);
        northPanel.add(txt_message);
        northPanel.add(btn_start);
        northPanel.add(btn_stop);
        northPanel.setBorder(new TitledBorder("基本信息"));
        this.setTitle("聊天室服务器端");
        this.setLayout(new BorderLayout());
        this.add(northPanel, "North");
        this.add(centerSplit, "Center");
        this.add(southPanel, "South");
        this.setSize(700, 500);
        // 设置边框不可以随意修改
        this.setResizable(false);
        //frame.setSize(Toolkit.getDefaultToolkit().getScreenSize());//设置全屏
        int screen_width = Toolkit.getDefaultToolkit().getScreenSize().width;
        int screen_height = Toolkit.getDefaultToolkit().getScreenSize().height;
        this.setLocation((screen_width - this.getWidth()) / 2,
                (screen_height - this.getHeight()) / 2);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setVisible(true);
        // 窗口关闭自动退出
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                if (isStart) {
                    // 关闭服务器
                    CloseServer();
                }
                // 退出程序
                System.exit(0);
            }
        });
        // 单击启动服务器按钮时事件
        btn_start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (isStart) {
                    JOptionPane.showMessageDialog(null, "服务器已处于启动状态，不要重复启动！",
                            "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    StartServer();
                    JOptionPane.showMessageDialog(null, "服务器成功启动!");
                    btn_start.setEnabled(false);
                    btn_stop.setEnabled(true);
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, exc.getMessage(),
                            "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        // 停止按钮
        btn_stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!isStart) {
                    JOptionPane.showMessageDialog(null, "服务器还未启动，无需停止！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }
                try {
                    CloseServer();
                    btn_start.setEnabled(true);
                    btn_stop.setEnabled(false);
                    contentArea.append("服务器成功停止!\r\n");
                    JOptionPane.showMessageDialog(null, "服务器成功停止！");
                } catch (Exception exc) {
                    JOptionPane.showMessageDialog(null, "停止服务器发生异常！", "错误",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }
    /**
     * 群发服务器消息
     * @param message
     */
    public void SendToMessage(ReturnMessageEntity message) {
        ResponseEntity responseEntity = new ResponseEntity();
        List<ResponseEntity> responseEntities = new ArrayList<>();
        List<ReturnMessageEntity>   returnMessageEntities = new ArrayList<>();
        returnMessageEntities.add(message);
        responseEntity.setChatGroupmessages(returnMessageEntities);
        responseEntities.add(responseEntity);
        contentArea.append("客户端"+"发送了一条消息\n");
        for (int i = clients.size() - 1; i >= 0; i--) {
            try {
                clients.get(i).getObjectOutputStream().writeObject(responseEntities);
                clients.get(i).getObjectOutputStream().flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * 私聊
     * @param message
     */
    public void SendToOne(ReturnMessageEntity message){
        ResponseEntity responseEntity = new ResponseEntity();
        List<ResponseEntity> responseEntities = new ArrayList<>();
        List<ReturnMessageEntity>   returnMessageEntities = new ArrayList<>();
        returnMessageEntities.add(message);
        responseEntity.setChatGroupmessages(returnMessageEntities);
        responseEntities.add(responseEntity);
        contentArea.append(message.getName()+"发送了一条消息\n");
        // 当消息的发送人等于接收人的时候则回发消息
        for (int j = 0; j < clients.size(); j++) {
            for(int i = 0; i <  clients.size(); i++) {
                // 如果线程相同则发送消息
                if (clientMap.get(clients.get(j).getMessageEntity().getReciver()) == (clients.get(i))) {
                    try {
                        clients.get(j).getObjectOutputStream().writeObject(responseEntities);
                        clients.get(j).getObjectOutputStream().flush();
                        break;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * 启动服务
     */
    public void StartServer() {
        try {
            clients = new ArrayList<>();
            // 存一对链接信息
            clientMap = new HashMap<>();
            // 存入链接类型
            type = new HashMap<>();
            serverSocket = new ServerSocket(TCPEntity.port);
            serverThread = new ServerThread(serverSocket);
            serverThread.start();
            isStart = true;
            Date dt = new Date();
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            contentArea.append("启动成功\n"+"当前时间："+sdf2.format(dt)+"\n");
//            System.out.println("启动成功");
        } catch (BindException e) {
            isStart = false;
            try {
                contentArea.append("端口号已被占用，请换一个");
                throw new BindException("端口号已被占用，请换一个");
            } catch (BindException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            isStart = false;
            try {
                contentArea.append("启动服务器异常！");
                throw new BindException("启动服务器异常！");
            } catch (BindException e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * 关闭服务器线程
     */
    public void CloseServer(){
        try {
            if (serverThread != null)     {
                serverThread.stop();// 停止服务器线程
            }
            for (int i = clients.size() - 1; i >= 0; i--) {
                // 释放资源
                // 停止此条为客户端服务的线程
                clients.get(i).stop();
                clients.get(i).getObjectOutputStream().close();
                clients.get(i).getObjectInputStream().close();
                clients.get(i).socket.close();
                clients.remove(i);
            }
            if (serverSocket != null) {
                serverSocket.close();// 关闭服务器端连接
            }
            listModel.removeAllElements();// 清空用户列表
            contentArea.append("关闭成功！\n");
            isStart = false;
        } catch (IOException e) {
            e.printStackTrace();
            isStart = true;
        }
    }
    /**
     * client线程
     */
    class ChatServer extends Thread{
        /**
         * 与本线程相关的socket
         */
        Socket socket = null;
        /**
         * 对象输入流
         */
        ObjectInputStream reader;
        /**
         *  对象输出流
         */
        ObjectOutputStream writer;
        /**
         * 消息发送实体
         */
        ReturnMessageEntity returnMessageEntity;
        /**
         * 接收消息实体
         */
        MessageEntity messageEntity;
        /**
         * 登陆消息实体
         */
        LoginEntity loginEntity;

        public ChatServer(Socket socket) {
            this.socket = socket;
            try {
                returnMessageEntity = new ReturnMessageEntity();
                returnMessageEntities = new ArrayList<>();
                messageEntity = new MessageEntity();
                loginEntity = new LoginEntity();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        public ObjectInputStream getObjectInputStream(){
            return reader;
        }

        public ObjectOutputStream getObjectOutputStream() {
            return writer;
        }

        public ReturnMessageEntity getReturnMessageEntity() {
            return returnMessageEntity;
        }

        public MessageEntity getMessageEntity(){
            return messageEntity;
        }

        public LoginEntity getLoginEntity() {
            return loginEntity;
        }

        // 不断接收客户端的消息，进行处理。
        @Override
        public void run() {
            Object message = null;
            List<ResponseEntity> result = null;
            // 发过来的对象中间包含的值
            Map<String,Object> resultValue = null;
            contentArea.append("[服务器消息]"+socket.getInetAddress()+"接入成功\n");
//            System.out.println("[服务器消息]"+socket.getInetAddress()+"接入成功");
            while (true){
                try {
                    reader = new ObjectInputStream(socket.getInputStream());
                    message =  reader.readObject();
                    messageEntity = (MessageEntity)message;
                    if (message!=null){
                        // 转为常规消息对象
                        // 解析对象
                        Class objectClass = message.getClass();
                        Field[] fields = objectClass.getDeclaredFields();
                        type = new HashMap<>();
                        fields[0].setAccessible(true);
                        // 存入类型
                        type.put(fields[0].getName(),fields[0].get(message).toString());
                        // 此处存入开发者真正的名称
                        fields[1].setAccessible(true);
                        type.put(fields[1].getName(),fields[1].get(message).toString());
                        // 重组链接Map，将原来占位的key变为正确的key值
                        for (int i = 0; i < clients.size() ; i++) {
                            // 如果当前链接是改链接则向map中插入一组数据
                            if (clients.get(i) == this){
                                listModel.addElement(messageEntity.getSenderName().toString());// 更新在线列表
                                contentArea.append(type.get("sender").toString() + "上线!\r\n");
                                clientMap.put(type.get("sender").toString(), clients.get(i));
                            }
                        }
                        // 将对象通过反射技术转换成map
                        // 如果是sendGroup 则群发消息
                        if(type.get("type").equals("sendGroup")){
                            // 请求数据库
                            MainAction mainAction = new MainAction();
                            result =  mainAction.DealWithAction(message);
                            resultValue = toObject(message);
                            // Map转成对象
                            returnMessageEntity = getMapToObj(resultValue);
                            // 测试多人发送
                            SendToMessage(getReturnMessageEntity());
                            // 如果是send则单发
                        }else if (type.get("type").equals("send")){
                            // 请求数据库
                            MainAction mainAction = new MainAction();
                            result = mainAction.DealWithAction(message);
                            resultValue = toObject(message);
                            // Map转成对象
                            returnMessageEntity = getMapToObj(resultValue);
                            SendToOne(getReturnMessageEntity());
                        }else if (type.get("type").equals("conn")){
                            contentArea.append("链接成功\n");
                        }else {
                            writer = new ObjectOutputStream(socket.getOutputStream());
                            // 请求数据库
                            MainAction mainAction = new MainAction();
                            result = mainAction.DealWithAction(message);
                            writer.writeObject(result);
                            writer.flush();
                        }
                    }

                } catch (Exception e) {
                    System.out.println("IO输出异常");
                    e.printStackTrace();
                }
            }
        }

        /**
         * 解析字段变成一个Map
         * @param object
         * @return
         */
        private Map<String,Object> toObject(Object object) throws Exception{
            Class objectClass = object.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            Map<String,Object> result = new HashMap<>();
            for (int i = 0; i < fields.length; i++) {
                fields[i].setAccessible(true);
                String colum = fields[i].getName();
                if(fields[i].getType().getName().equals("java.lang.String")){
                    String value =  fields[i].get(object).toString();
                    if(value != null){
                        result.put(colum,value);
                    } else {
                        continue;
                    }
                }else if(fields[i].getType().getName().equals("java.lang.Integer")){
                    String value = String.valueOf(fields[i].getInt(object));
                    if(value != null){
                        result.put(colum,value);
                    } else {
                        continue;
                    }
                }else if(fields[i].getType().getName().equals("java.util.Date")){
                    Date value = (Date) fields[i].get(object);
                    if(value != null){
                        result.put(colum,value);
                    } else {
                        continue;
                    }
                }
            }
            return result;
        }
        /**
         * Map转对象
         * @param resultValue
         * @return
         */
        private ReturnMessageEntity getMapToObj(Map<String, Object> resultValue){
            Date dt = (Date) resultValue.get("senderTime");
            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
            System.out.println("时间："+sdf2.format(dt)+" 发送人:"+resultValue.get("senderName")+" 内容："+ resultValue.get("content"));
            ReturnMessageEntity returnMessageEntity = new ReturnMessageEntity();
            returnMessageEntity.setTime(sdf2.format(dt).toString());
            returnMessageEntity.setName(resultValue.get("senderName").toString());
            returnMessageEntity.setContent(resultValue.get("content").toString());
            return returnMessageEntity;
        }
    }
    /**
     * ServerThread
     */
    class ServerThread extends Thread {

        private ServerSocket serverSocket;

        public ServerThread(ServerSocket serverSocket) {
            this.serverSocket = serverSocket;
        }

        @Override
        public void run() {
            // 不停的等待客户端的链接
            while (true) {
                try {
                    Socket socket = serverSocket.accept();
                    ChatServer client = new ChatServer(socket);
                    client.start();// 开启对此客户端服务的线程
                    clients.add(client);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void StartServerService() {
        // 启动GUI
        MultUserService server = new MultUserService();
    }

}
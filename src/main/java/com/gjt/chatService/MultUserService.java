package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Field;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/24/20:14
 */
public class MultUserService {

    private ServerSocket serverSocket;

    private ArrayList<ChatServer> clients;

    private List<Map<String,ChatServer>> clientList;

    private ServerThread serverThread;

    private List<ReturnMessageEntity> returnMessageEntities;

    private boolean isStart;

    public MultUserService() {}

    /**
     * 群发服务器消息
     * @param message
     */
    public void SendToMessage(ReturnMessageEntity message) {
        returnMessageEntities.add(message);
        for (int i = clients.size() - 1; i >= 0; i--) {
            try {
                clients.get(i).getObjectOutputStream().writeObject(returnMessageEntities);
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
        returnMessageEntities.add(message);
//        for (int i = clients.size() - 1; i >= 0; i--) {
//            for(int j = clients.size() - 1; j >= 0; j--){
//                try {
//                    if (clients.get(i).getMessageEntity().getReciver().equals(clients.get(j).getMessageEntity().getSender())){
//                        clients.get(i).getObjectOutputStream().writeObject(returnMessageEntities);
//                        clients.get(i).getObjectOutputStream().flush();
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        }j                                                                     j
        for (int i = clientList.size() - 1; i >= 0; i--) {
            for(int j = clients.size() - 1; j >= 0; j--) {
                if (clientList.get(i).get("sender").equals(clients.get(j).getMessageEntity().getReciver())) {
                    try {
                        clients.get(j).getObjectOutputStream().writeObject(returnMessageEntities);
                        clients.get(j).getObjectOutputStream().flush();
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
            clientList = new ArrayList<>();
            serverSocket = new ServerSocket(TCPEntity.port);
            serverThread = new ServerThread(serverSocket);
            serverThread.start();
            isStart = true;
        } catch (BindException e) {
            isStart = false;
            try {
                throw new BindException("端口号已被占用，请换一个！");
            } catch (BindException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
            isStart = false;
            try {
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
//            listModel.removeAllElements();// 清空用户列表
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
                reader = new ObjectInputStream(socket.getInputStream());
                writer = new ObjectOutputStream(socket.getOutputStream());
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
            while (true){
                try {
                    System.out.println("[服务器消息]"+socket.getInetAddress()+"接入成功");
                    message = reader.readObject();
                    // 解析对象
                    Class objectClass = message.getClass();
                    Field[] fields = objectClass.getDeclaredFields();
                    Map<String,String> type = new HashMap<>();
                    fields[0].setAccessible(true);
                    // 存入类型
                    type.put(fields[0].getName(),fields[0].get(message).toString());
                    // 存入开发者名称
                    fields[1].setAccessible(true);
                    type.put(fields[1].getName(),fields[1].get(message).toString());
                    Map<String,ChatServer> clientMap; clientMap = new HashMap<>();
                    clientMap.put(type.get("ender").toString(), this);
                    clientList.add(clientMap);

                    // 写入数据库
                    MainAction mainAction = new MainAction();
                    result = mainAction.DealWithAction(message);
                    // 将对象通过反射技术转换成map
                    resultValue = toObject(message);
                    Thread.sleep(100);
                    // 如果是sendGroup 则群发消息
                    if(type.get("type").equals("sendGroup")){
                        returnMessageEntity = getMapToObj(resultValue);
                        // 测试多人发送
                        SendToMessage(getReturnMessageEntity());
                        // 如果是send则单发
                    }else if (type.get("type").equals("send")){
                        returnMessageEntity = getMapToObj(resultValue);
                        // 转为常规消息对象
                        messageEntity = (MessageEntity)message;
                        SendToOne(getReturnMessageEntity());
                    }else {
                        writer.writeObject(result);
                        writer.flush();
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

        private ReturnMessageEntity getMapToObj(Map<String, Object> resultValue){
            Date dt = (Date) resultValue.get("senderTime");
            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
            System.out.println("时间："+sdf2.format(dt)+"发送人:"+resultValue.get("senderName")+"内容："+ resultValue.get("content"));
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
            while (true) {// 不停的等待客户端的链接
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

    public static void main(String[] args) {
        MultUserService server = new MultUserService();
        server.StartServer();
    }

}
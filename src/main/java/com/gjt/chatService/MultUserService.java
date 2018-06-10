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

    private Map<String,ChatServer> clientMap;

    private ServerThread serverThread;

    private List<ResponseEntity> returnMessageEntities;

    private Map<String,String> type;

    private Boolean isStart;

    private int connNum;

    public MultUserService() {}
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
        System.out.println("未找到合适的人");
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
            System.out.println("启动成功");
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
            System.out.println("[服务器消息]"+socket.getInetAddress()+"接入成功");
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
                            System.out.println("链接成功");
                        }else {
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

    public static void main(String[] args) {
        MultUserService server = new MultUserService();
        server.StartServer();
    }

}
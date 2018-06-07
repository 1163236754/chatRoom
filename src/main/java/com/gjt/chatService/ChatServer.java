package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.ResponseEntity;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  服务端
 * @date 2018/5/24/19:59
 */
class ChatServer implements Runnable{

    /**
     * 与本线程相关的socket
     */
    Socket socket = null;

    /**
     * 存放客户端之间私聊的信息
     */
    private  Map<String,PrintWriter> storeInfo = null;

    public ChatServer(Socket socket, Map<String, PrintWriter> storeInfo) {
        this.socket = socket;
        this.storeInfo = storeInfo;

    }

    /**
     *  将客户端的信息以Map形式存入集合中
     * @param key
     * @param value
     */
    private void putIn(String key,PrintWriter value) {
        synchronized(this) {
            storeInfo.put(key, value);
        }
        return;
    }

    /**
     * 将给定的输出流从共享集合中删除
     * @param key
     */
    private synchronized void remove(String  key) {
        storeInfo.remove(key);
        System.out.println("当前在线人数为："+ storeInfo.size());
        return;
    }

    /**
     * 将消息发给所有客户端
     * @param message
     */
    private synchronized void sendToAll(String message) {
        for(PrintWriter out: storeInfo.values()) {
            out.println(message);
            out.flush();
        }
        return;
    }

    @Override
    public void run() {
        Object obj = null;
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        List<ResponseEntity> result = null;
        // 发过来的对象中间包含的值
        Map<String,Object> resultValue = null;
        try {
            is = socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);
            obj = ois.readObject();
            // 解析对象
            Class objectClass = obj.getClass();
            Field[] fields = objectClass.getDeclaredFields();
            Map<String,String> type = new HashMap<>();
            fields[0].setAccessible(true);
            type.put(fields[0].getName(),fields[0].get(obj).toString());
            // 写入数据库
            if (obj != null){
                System.out.println("接收到来自["+socket.getInetAddress().getHostAddress()+"]的数据");
                if(type.get("type").equals("sendGroup")){
                    /*
                     * 通过客户端的Socket获取客户端的输出流
                     * 用来将消息发送给客户端
                     */
                    pw = new PrintWriter(
                            new OutputStreamWriter(socket.getOutputStream(), "UTF-8"), true);
                    resultValue = toObject(obj);
                    /*
                     * 将客户昵称和其所说的内容存入共享集合HashMap中
                     */
                    putIn(resultValue.get("senderName").toString(), pw);
                    Thread.sleep(100);
                    // 服务端通知所有客户端，某用户上线
                    sendToAll("[系统通知] “" + resultValue.get("senderName").toString() + "”已上线");
                    Date dt = (Date) resultValue.get("senderTime");
                    SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
                    SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    // 遍历所有输出流，将该客户端发送的信息转发给所有客户端
                    System.out.println("时间："+sdf2.format(dt)+"发送人:"+resultValue.get("senderName")+"内容："+ resultValue.get("content"));
                    sendToAll("\n时间："+sdf2.format(dt).toString()+"\n发送人:"+resultValue.get("senderName").toString()+"\n内容："+ resultValue.get("content").toString());
                }
            }
            MainAction mainAction = new MainAction();
            result = mainAction.DealWithAction(obj);
            os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(result);
            oos.flush();
            socket.shutdownOutput();
        } catch (Exception e) {
            e.printStackTrace();
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
}

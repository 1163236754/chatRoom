package com.gjt.chatService.utils;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.ResponseEntity;

import java.io.*;
import java.lang.reflect.Field;
import java.net.Socket;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/8/11:34
 */
public class ChatServer implements Runnable{
    /**
     * 与本线程相关的socket
     */
    Socket socket = null;

    /**
     * 存放客户端信息
     */

    public ChatServer(Socket socket) {
        this.socket = socket;
    }
    @Override
    public void run() {
        Object obj = null;
        OutputStream os = null;
        PrintWriter pw = null;
        InputStream is = null;
        List<ResponseEntity> result = null;
        try {
            is = socket.getInputStream();
            ObjectInputStream ois=new ObjectInputStream(is);
            obj = ois.readObject();
            if (obj != null){
                System.out.println("接收到来自["+socket.getInetAddress().getHostAddress()+"]的数据");
                MainAction mainAction = new MainAction();
                result = mainAction.DealWithAction(obj);
            }
            os = socket.getOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(os);
            oos.writeObject(result);
            oos.writeObject(null);
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

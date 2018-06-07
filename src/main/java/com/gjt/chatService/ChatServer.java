package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.ResponseEntity;

import java.io.*;
import java.net.Socket;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  多文件上传服务端
 * @date 2018/5/24/19:59
 */

public class ChatServer implements Runnable{

    /**
     * 与本线程相关的socket
     */
    Socket socket = null;



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
}

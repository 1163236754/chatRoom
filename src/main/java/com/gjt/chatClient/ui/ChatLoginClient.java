package com.gjt.chatClient.ui;

import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.utils.MessageUtils;
import com.gjt.chatService.utils.TcpSocketUtils;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Date;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: Login客户端
 * @date 2018/5/24/9:50
 */
public class ChatLoginClient {

    /**
     * 建立客户端socket连接
     */
    private static TcpSocketUtils conn  = new TcpSocketUtils();

    /**
     * 设置登陆信息
     */
    private static LoginEntity loginEntity = new LoginEntity();

    /**
     * 启动文件工具类
     */
    private static MessageUtils userUtils = new MessageUtils();

    /**
     * 发送登陆验证
     * @throws Exception
     */
    private static void SendMessage() throws Exception {
        Socket connect = conn.ConnectTcpClient();
        OutputStream os = connect.getOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(os);
        oos.writeObject(loginEntity);
        oos.flush();
        connect.shutdownOutput();
        InputStream is = connect.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(is);
        List<ResponseEntity > obj = (List<ResponseEntity>) ois.readObject();
        String result = null;
        if(obj != null){
            for (int i = 0; i < obj.size(); i++) {
                if(obj.get(i).getResponseContent().equals("登陆成功")){
                    result = "登陆成功";
                }else if (obj.get(i).getResponseContent().equals("登陆失败")){
                    result = "登陆失败";
                }
            }
        }
        if(result.equals("登陆成功")){
            connect.shutdownInput();
            oos.close();
            os.close();
            is.close();
            ois.close();
            conn.Close(connect);
            Chat chat = new Chat();
            chat.StartThread();
        }else {
            connect.shutdownInput();
            oos.close();
            os.close();
            is.close();
            ois.close();
            conn.Close(connect);
            String[] str = new String[0];
            // 失败回调
            main(str);
        }

    }
    /**
     * 初始化登陆组装
     * @param userName
     * @param password
     */
    private static void initFiles(String userName,String password) {
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
    public static void main(String[] args) {
        String userName = "11621380110";
        String password = "123456";
        initFiles(userName,password);
        try {
            SendMessage();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

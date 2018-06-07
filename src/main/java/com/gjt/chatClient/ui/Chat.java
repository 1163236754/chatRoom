package com.gjt.chatClient.ui;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.entity.ResponseEntity;
import com.gjt.chatService.entity.TCPEntity;
import com.gjt.chatService.utils.TcpSocketUtils;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  聊天
 * @date 2018/6/1/16:50
 */
public class Chat{
    /**
     * 建立客户端socket连接
     */
    private  TcpSocketUtils conn  = new TcpSocketUtils();



    public void StartThread(){
        Socket connect = conn.ConnectTcpClient();
        new Thread(new SendThread(connect)).start();
//        new Thread(new ReceiveThread(connect)).start();
        // 接收服务器端发送过来的信息的线程启动
        ExecutorService exec = Executors.newCachedThreadPool();
        exec.execute(new ListenrServser(connect));
        new Thread(new ListenrServser(connect)).start();
    }
}

/**
 * 发送数据线程
 */
class SendThread implements Runnable {
    /**
     * 接收多线程
     */
    private Socket client;
    /**
     * 是否运行
     */
    private boolean isRun = true;
    /**
     * Send Message
     */
    MessageEntity messageEntity = new MessageEntity();

    public SendThread(Socket client) {
        this.client = client;
    }
    /**
     * 发送消息
     */
    public void Send() {
        //判断输出流是否已经关闭，如果关闭就重新new Socket
        if (client.isOutputShutdown()) {
            try {
                client = new Socket(TCPEntity.conAddr, TCPEntity.port);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream os = null;
        ObjectOutputStream oos = null;
        try {

            String ip = InetAddress.getLocalHost().getHostAddress();
            os = client.getOutputStream();
            oos = new ObjectOutputStream(os);
            if(messageEntity.getContent() != null){
                messageEntity.setSenderIp(ip);
                oos.writeObject(messageEntity);
                oos.writeObject(null);
                oos.flush();
            }
            client.shutdownOutput();
        }catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if(os != null ){
                    os.close();
                }
                if(oos != null){
                    oos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ;
    }

    /**
     * 这里是手输入的地方
     */
    public void SendMessage(){
        System.out.println("这里发送消息");
        messageEntity.setType("sendGroup");
        messageEntity.setSender("11621380110");
        // 这里可以设置群或者收信人
        messageEntity.setReciver("116213801");
        messageEntity.setSenderName("官江涛");
        messageEntity.setSenderTime(new Date());
        System.out.println("请输入你要输入的发送的");
        while (isRun){
            Scanner sc = new Scanner(System.in);
            String content = sc.next();
            messageEntity.setContent(content);
            try {
                Send();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    @Override
    public void run() {
        while (isRun) {
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
class ReceiveThread implements Runnable {
    private Socket client;
    private boolean isRun = true;

    /**
     * date time start
     */
    private static long start;

    /**
     * Get Message
     */
//    GetMessageEntity getMessageEntity = new GetMessageEntity();

    public ReceiveThread(Socket client) {
        this.client = client;
    }

    /**
     * 消息接收
     * @throws Exception
     */
    public void Recevice() throws Exception {
//        //判断输出流是否已经关闭，如果关闭就重新new Socket
//        if (client.isOutputShutdown()) {
//            try {
//                client = new Socket(TCPEntity.conAddr, TCPEntity.port);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//        OutputStream os = client.getOutputStream();
//        ObjectOutputStream oos = new ObjectOutputStream(os);
//        if(getMessageEntity.getGroupId() != null){
//            oos.writeObject(getMessageEntity);
//            oos.writeObject(null);
//            oos.flush();
//        }
//        client.shutdownOutput();
        InputStream is = client.getInputStream();
        ObjectInputStream ois=new ObjectInputStream(is);
        List<ResponseEntity > obj = (List<ResponseEntity>) ois.readObject();
        String result = null;
        List<ChatGroupmessage> chatGroupmessages = new ArrayList<>();
        if(obj != null){
            for (int i = 0; i < obj.size(); i++) {
                if(obj.get(i).getResponseContent().equals("发送成功")){
                    result = "发送成功";
                }else if (obj.get(i).getResponseContent().equals("发送失败")){
                    result = "发送失败";
                }else if(obj.get(i).getResponseContent().equals("接收成功")){
                    if(obj.get(0).getChatGroupmessages()!=null){
                        for(int j = 0;j < obj.get(i).getChatGroupmessages().size();j++){
                            // 设置群消息
                            chatGroupmessages.add(obj.get(i).getChatGroupmessages().get(j));
                            // 设置私人消息
                        }
                    }
                }else if(obj.get(i).getResponseContent().equals("接收失败")){
                    result = "接收失败";
                }
            }
        }
//        PrintMessage(chatGroupmessages);
        PrintGroupMessage(chatGroupmessages);
        client.shutdownInput();
        return;
    }

    public void PrintGroupMessage(List<ChatGroupmessage> chatGroupmessages){
        for (int i = 0; i < chatGroupmessages.size(); i++) {
//            String dt = chatGroupmessages.get(i).getSendTime().toString();
//            SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
//            SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("时间：" + chatGroupmessages.get(i).getSendTime().toString());
            System.out.println("来自："+chatGroupmessages.get(i).getSender());
            System.out.println("内容："+chatGroupmessages.get(i).getContent());

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
//            getMessageEntity.setGroupId("116213801");
//            getMessageEntity.setType("groupMessage");
            try {
//                Thread.sleep(3000);
                Recevice();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
// 监听群消息
class ListenrServser implements Runnable {

    Socket connect = null;

    public ListenrServser(Socket connect){
        this.connect = connect;
    }

    @Override
    public void run() {
        try {
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(connect.getInputStream(), "UTF-8"));
            String msgString;
            while((msgString = br.readLine())!= null) {
                System.out.println(msgString);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }

    }
}

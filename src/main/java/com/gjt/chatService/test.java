package com.gjt.chatService;

import com.gjt.chatService.action.MainAction;
import com.gjt.chatService.entity.MessageEntity;

import java.util.Scanner;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  测试类
 * @date 2018/6/3/17:29
 */
public class test {

    public static void main(String[] args) {
        MainAction mainAction = new MainAction();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setType("sendGroup");
        messageEntity.setSender("11621380110");
        messageEntity.setGroupId("116213801");
        System.out.println("请输入你要输入的发送的");
        while (true){
            Scanner sc = new Scanner(System.in);
            String content = sc.next();
            messageEntity.setContent(content);
            try {
                mainAction.DealWithAction(messageEntity);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package com.gjt.chatService.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 消息实体
 * @date 2018/6/3/21:14
 */
public class MessageEntity implements Serializable{

    /**
     * type用于判断数据类型
     * 分为
     * 其中标号为1的是获取消息
     * 未标号的为发送消息
     * send     发送消息给数据库
     * request  向数据库请求消息           1
     * groupMessage 向数据库请求消息       1
     * sendGroup    发送群消息
     * requestFriendList  请求好友列表     1
     * addFriend   加好友
     */
    private String type;
    /**
     * 接收人名称
     */
    private String sender;
    /**
     * 接收人名称
     * 可以是人或者群
     * 或者请求的好友
     */
    private String reciver;
    /**
     * 发送人IP
     */
    private String sendIp;
    /**
     * 发送内容
     */
    private String content;

    /**
     * 发送者名称
     */
    private String senderName;
    /**
     * 发送时间，专为群聊定制
     */
    private Date senderTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReciver() {
        return reciver;
    }

    public void setReciver(String reciver) {
        this.reciver = reciver;
    }

    public String getSenderIp() {
        return sendIp;
    }

    public void setSenderIp(String sendIp) {
        this.sendIp = sendIp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
//
//    public String getGroupId() {
//        return groupId;
//    }
//
//    public void setGroupId(String groupId) {
//        this.groupId = groupId;
//    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public Date getSenderTime() {
        return senderTime;
    }

    public void setSenderTime(Date senderTime) {
        this.senderTime = senderTime;
    }
}

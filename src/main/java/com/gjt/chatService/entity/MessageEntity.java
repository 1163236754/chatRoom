package com.gjt.chatService.entity;

import java.io.Serializable;

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
     * send     发送消息给数据库
     * request  向数据库请求消息
     * groupMessage 向数据库请求消息
     * sendGroup    发送群消息
     */
    private String type;
    /**
     * 接收人名称
     */
    private String sender;
    /**
     * 接收人名称
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
     * 群ID
     */
    private String groupId;

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

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

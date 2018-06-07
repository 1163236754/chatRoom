package com.gjt.chatService.entity;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/4/10:03
 */
public class Message {

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
     * 群Id
     */
    private String groupId;

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

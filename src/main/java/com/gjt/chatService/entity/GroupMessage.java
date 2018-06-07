package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/6/11:09
 */
public class GroupMessage implements Serializable{
    /**
     * 发送人
     */
    private String sender;
    /**
     * 群号
     */
    private String groupNumber;
    /**
     * 内容
     */
    private String content;
    /**
     * 发送地址
     */
    private String sendIp;

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(String groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSendIp() {
        return sendIp;
    }

    public void setSendIp(String sendIp) {
        this.sendIp = sendIp;
    }
}

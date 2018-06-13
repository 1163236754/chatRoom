package com.gjt.chatService.entity;

import com.gjt.chat.entity.ChatMessage;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 响应实体
 * 所有消息经过这个实体响应获取
 * @date 2018/6/4/15:57
 */
public class ResponseEntity implements Serializable {

    private String statusCode;
    /**
     * 包括
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
     * 状态
     * success
     * err
     */
    private String responseContent;
    /**
     * 私人消息
     */
    private List<ChatMessage> messagesData;
    /**
     * 群消息
     */
    private List<ReturnMessageEntity> chatGroupmessages;
    /**
     * 登陆消息
     */
    private Map<String,Object> loginMessage;
    /**
     * 在线人数
     */
    private int onlineNum;
    /**
     * 在线好友信息
     */
    private List<UserEntity> userEntities;

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ChatMessage> getMessagesData() {
        return messagesData;
    }

    public void setMessagesData(List<ChatMessage> messagesData) {
        this.messagesData = messagesData;
    }

    public List<ReturnMessageEntity> getChatGroupmessages() {
        return chatGroupmessages;
    }

    public void setChatGroupmessages(List<ReturnMessageEntity> chatGroupmessages) {
        this.chatGroupmessages = chatGroupmessages;
    }

    public Map<String, Object> getLoginMessage() {
        return loginMessage;
    }

    public void setLoginMessage(Map<String, Object> loginMessage) {
        this.loginMessage = loginMessage;
    }

    public int getOnlineNum() {
        return onlineNum;
    }

    public void setOnlineNum(int onlineNum) {
        this.onlineNum = onlineNum;
    }

    public List<UserEntity> getUserEntities() {
        return userEntities;
    }

    public void setUserEntities(List<UserEntity> userEntities) {
        this.userEntities = userEntities;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
    }
}

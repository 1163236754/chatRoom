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

    public String getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getResponseContent() {
        return responseContent;
    }

    public void setResponseContent(String responseContent) {
        this.responseContent = responseContent;
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
}

package com.gjt.chatService.entity;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;

import java.io.Serializable;
import java.util.List;

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
    private List<ChatGroupmessage> chatGroupmessages;

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

    public List<ChatGroupmessage> getChatGroupmessages() {
        return chatGroupmessages;
    }

    public void setChatGroupmessages(List<ChatGroupmessage> chatGroupmessages) {
        this.chatGroupmessages = chatGroupmessages;
    }
}

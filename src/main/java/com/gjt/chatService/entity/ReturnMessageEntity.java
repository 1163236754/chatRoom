package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 返回消息
 * @date 2018/6/7/22:04
 */
public class ReturnMessageEntity implements Serializable{
    // 发送时间
    private String time;
    // 发送人
    private String name;
    // 发送内容
    private String content;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

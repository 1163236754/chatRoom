package com.gjt.chat.entity;

import java.io.Serializable;
import java.util.Date;

public class ChatGroupmessage implements Serializable{
    private Integer id;

    private String name;

    private String groupnumber;

    private Date send_time;

    private String sendip;

    private Date createdAt;

    private Date updatedAt;

    private String content;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSender() {
        return name;
    }

    public void setSender(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGroupnumber() {
        return groupnumber;
    }

    public void setGroupnumber(String groupnumber) {
        this.groupnumber = groupnumber == null ? null : groupnumber.trim();
    }

    public Date getSendTime() {
        return send_time;
    }

    public void setSendTime(Date send_time) {
        this.send_time = send_time;
    }

    public String getSendip() {
        return sendip;
    }

    public void setSendip(String sendip) {
        this.sendip = sendip == null ? null : sendip.trim();
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}
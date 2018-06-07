package com.gjt.chat.entity;

import java.util.Date;

public class ChatGroupChat {
    private Integer id;

    private String groupNum;

    private String groupNickName;

    private Integer groupcapacity;

    private String groupmasterid;

    private Date createdAt;

    private Date updatedAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupNum() {
        return groupNum;
    }

    public void setGroupNum(String groupNum) {
        this.groupNum = groupNum == null ? null : groupNum.trim();
    }

    public String getGroupNickName() {
        return groupNickName;
    }

    public void setGroupNickName(String groupNickName) {
        this.groupNickName = groupNickName == null ? null : groupNickName.trim();
    }

    public Integer getGroupcapacity() {
        return groupcapacity;
    }

    public void setGroupcapacity(Integer groupcapacity) {
        this.groupcapacity = groupcapacity;
    }

    public String getGroupmasterid() {
        return groupmasterid;
    }

    public void setGroupmasterid(String groupmasterid) {
        this.groupmasterid = groupmasterid == null ? null : groupmasterid.trim();
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
}
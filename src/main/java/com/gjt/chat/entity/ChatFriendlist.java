package com.gjt.chat.entity;

public class ChatFriendlist {
//    private Integer id;

    private String friendid;

    private String friend_group;

    private String userid;

//    private Date createdAt;
//
//    private Date updatedAt;

//    public Integer getId() {
//        return id;
//    }
//
//    public void setId(Integer id) {
//        this.id = id;
//    }

    public String getFriendid() {
        return friendid;
    }

    public void setFriendid(String friendid) {
        this.friendid = friendid == null ? null : friendid.trim();
    }

    public String getFriendGroup() {
        return friend_group;
    }

    public void setFriendGroup(String friendGroup) {
        this.friend_group = friendGroup == null ? null : friendGroup.trim();
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid == null ? null : userid.trim();
    }

//    public Date getCreatedAt() {
//        return createdAt;
//    }
//
//    public void setCreatedAt(Date createdAt) {
//        this.createdAt = createdAt;
//    }
//
//    public Date getUpdatedAt() {
//        return updatedAt;
//    }
//
//    public void setUpdatedAt(Date updatedAt) {
//        this.updatedAt = updatedAt;
//    }
}
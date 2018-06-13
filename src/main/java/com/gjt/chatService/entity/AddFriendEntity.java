package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 新增好友实体
 * @date 2018/6/12/19:31
 */
public class AddFriendEntity implements Serializable {
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
     * 请求Id
     */
    private String userId;
    /**
     * 申请添加好友Id
     */
    private String friendId;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }
}

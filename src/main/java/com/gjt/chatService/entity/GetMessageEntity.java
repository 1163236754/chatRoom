package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  获取消息实体
 * @date 2018/6/4/10:32
 */
public class GetMessageEntity implements Serializable{

    /**
     * type用于判断数据类型
     * 分为
     * send     发送消息给数据库
     * request  向数据库请求消息
     * groupMessage 向数据库请求消息
     * sendGroup    发送群消息
     */
    private String type;
    /**
     * 当前登陆账号
     */
    private String isLoginId;
    /**
     * 请求群号
     */
    private String groupId;

    public String getIsLoginId() {
        return isLoginId;
    }

    public void setIsLoginId(String isLoginId) {
        this.isLoginId = isLoginId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }
}

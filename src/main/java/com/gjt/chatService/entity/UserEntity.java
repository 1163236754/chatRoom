package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  用户基本信息实体
 * @date 2018/6/12/0:08
 */
public class UserEntity implements Serializable{
    /**
     * 用户名
     */
    private String username;
    /**
     * 昵称
     */
    private String nickName;
    /**
     * 姓名
     */
    private String name;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

package com.gjt.chatService.entity;

import java.io.Serializable;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 登陆实体
 * @date 2018/6/1/16:18
 */
public class LoginEntity implements Serializable{

    public String type = "Login";

    /**
     * 账号
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 登陆ip地址
     */
    private String ipAddress;
    /**
     * 上线时间
     */
    private String loginTime;

    public String getUserName() {
        return username;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}


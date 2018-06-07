package com.gjt.chat.entity;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  展示列表
 * @date 2018/5/31/22:03
 */
public class ChatShowInfoList {

    /**
     * id
     */
    private Integer id;
    /**
     * 学号
     */
    private String stuId;
    /**
     * 名称
     */
    private String name;

    /**
     * 班级编号
     */
    private String classId;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 电话
     */
    private String phone;

    /**
     * 是否在线
     */
    private String isOnline;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(String isOnline) {
        this.isOnline = isOnline;
    }
}

package com.gjt.chat.entity;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/31/22:31
 */
public class ChatGetAll {
    /**
     * 页码
     */
    private String page;
    /**
     * 单页显示数量
     */
    private String pageSize;
    /**
     * stuId
     */
    private String stuId;
    /**
     * classId
     */
    private String classId;

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPageSize() {
        return pageSize;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }

    public String getStuId() {
        return stuId;
    }

    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }
}

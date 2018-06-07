package com.gjt.chatService.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:   端数据库链接工具类
 * @date 2018/6/1/21:23
 */
public class DButils {
    /**
     * 加载数据库驱动
     */
    private static String driver;
    /**
     * 数据库地址
     */
    private static String url;
    /**
     * 用户名
     */
    private static String User;
    /**
     * 密码
     */
    private static String password;
    /**
     * 初始化加载数据库配置资源
     */
    static{
        Properties prop = new Properties();
        BufferedInputStream in;
        try {
            in = new BufferedInputStream(File.class.getResourceAsStream("/config/config.properties"));
            prop.load(in);
        } catch (Exception e) {
            e.printStackTrace();
        }
        driver = prop.getProperty("driver");
        url = prop.getProperty("url");
        User = prop.getProperty("User");
        password = prop.getProperty("password");
    }

    public static Connection open() {
        try {
            Class.forName(driver);
            System.out.println("数据库链接成功");
            return DriverManager.getConnection(url,User,password);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static void close(Connection conn){
        if(conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

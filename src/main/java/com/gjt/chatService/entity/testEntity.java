package com.gjt.chatService.entity;

import com.gjt.chatService.utils.KeyWord;
import com.gjt.chatService.utils.Sql;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  数据库SQL测试类
 * @date 2018/6/3/9:54
 */
public class testEntity {
    public static void main(String[] args) {
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setId("username");
        sqlEntity.setContent("11621380110");
        sqlEntity.setTableName(KeyWord.DB_chat_loginmessage);
        Sql baseSQL = new Sql(sqlEntity);


        LoginEntity loginEntity = new LoginEntity();

        loginEntity.setUserName("11621380110");
        loginEntity.setPassword("123456");

        sqlEntity.setObject(loginEntity);

        try {
            sqlEntity.setSqlContent(sqlEntity.getObjectBySQL());
            System.out.println(baseSQL.BaseUpdateSQL() + baseSQL.BaseWhereSQL());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

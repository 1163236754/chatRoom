package com.gjt.chatService.daoImpl;

import com.gjt.chat.entity.ChatFriendlist;
import com.gjt.chatService.dao.UserDao;
import com.gjt.chatService.entity.AddFriendEntity;
import com.gjt.chatService.entity.SqlEntity;
import com.gjt.chatService.entity.UserEntity;
import com.gjt.chatService.utils.KeyWord;
import com.gjt.chatService.utils.Sql;
import com.gjt.chatService.utils.SqlUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/12/0:12
 */
public class UserDaoImpl implements UserDao{
    /**
     * 加载数据库查询组件
     */
    SqlUtils sqlUtils = new SqlUtils();

    /**
     * 获取当前在线人数人员
     * 其中userName为 查询的ID
     * @param userName
     * @return
     */
    @Override
    public List<UserEntity> GetOnlineUser(String userName) {
        List<UserEntity> userEntities = new ArrayList<>();
        String sql = "SELECT clm.username,cui.nick_name,cui.`name`FROM`chat_loginmessage` clm \n" +
                "LEFT JOIN chat_userinformation cui ON clm.username = cui.stu_id\n" +
                "LEFT JOIN chat_friendlist cfl ON cfl.friendId = cui.stu_id\n" +
                "WHERE\n" +
                "\tis_login = 1 AND cfl.userId = "+userName;
        List<Map<String, Object>> result = sqlUtils.QueryList(sql);
        if (result != null) {
            for (int i = 0; i < result.size(); i++) {
                try {
                    UserEntity userEntity = new UserEntity();
                    userEntity.setName(result.get(i).get("name").toString());
                    userEntity.setNickName(result.get(i).get("nick_name").toString());
                    userEntity.setUsername(result.get(i).get("username").toString());
                    userEntities.add(userEntity);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return userEntities;
    }
    /**
     * 新增好友
     * @param addFriendEntity
     * @return
     */
    @Override
    public int AddNewFrined(AddFriendEntity addFriendEntity) {
        ChatFriendlist chatFriendlist = new ChatFriendlist();
        chatFriendlist.setUserid(addFriendEntity.getUserId());
        chatFriendlist.setFriendGroup("默认分组");
        chatFriendlist.setFriendid(addFriendEntity.getFriendId());
        SqlEntity sqlEntity = new SqlEntity();
        // 设置表名称
        sqlEntity.setTableName(KeyWord.DB_chat_friendlist);
        // 解析对象
        sqlEntity.setObject(chatFriendlist);
        // 启动SQL生成工具
        Sql baseSQL = new Sql(sqlEntity);
        String sql = null;
        try {
            // 生成sql
            sql = baseSQL.BaseValueSQL();
            System.out.println("群消息记录表，生成的SQL语句是"+sql);
        } catch (Exception e) {
            System.out.println("sql语句有问题，请检查：" + sql);
            e.printStackTrace();
        }
        int InsertMessage = sqlUtils.Update(sql);
        if(InsertMessage == 1){
            return 1;
        }
        return 0;
    }
}

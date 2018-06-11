package com.gjt.chatService.daoImpl;

import com.gjt.chatService.dao.UserDao;
import com.gjt.chatService.entity.UserEntity;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/12/0:12
 */
public class UserDaoImpl implements UserDao{
    /**
     * 获取当前在线人数
     * @return
     */
//
//    SELECT
//    clm.username,
//    cui.nick_name,
//    cui.`name`
//    FROM
//	`chat_loginmessage` clm
//    LEFT JOIN chat_userinformation cui ON clm.username = cui.stu_id
//    LEFT JOIN chat_friendlist cfl ON cf1 cfl.userId = cui.stu_id
//            WHERE
//    is_login = 1 and cfl.userId = '1163236754';
    @Override
    public List<UserEntity> GetOnlineUser() {
        String sql = "SELECT clm.username,cui.nick_name,cui.`name` FROM `chat_loginmessage` clm LEFT JOIN chat_userinformation cui ON clm.username = cui.stu_id WHERE is_login = 1;" ;
        return null;
    }
}

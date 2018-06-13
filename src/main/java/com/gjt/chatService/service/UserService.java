package com.gjt.chatService.service;

import com.gjt.chatService.dao.UserDao;
import com.gjt.chatService.daoImpl.UserDaoImpl;
import com.gjt.chatService.entity.AddFriendEntity;
import com.gjt.chatService.entity.UserEntity;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/12/18:54
 */
public class UserService {

    /**
     * 根据账号获取在线人员的基本消息
     * @param username
     * @return
     */
    public List<UserEntity> GetOnlineUsersById(String username){
        UserDao userDao = new UserDaoImpl();
        return userDao.GetOnlineUser(username);
    }
    /**
     * 新增好友
     * @param addFriendEntity
     * @return
     */
    public int AddFriend(AddFriendEntity addFriendEntity){
       UserDao userDao = new UserDaoImpl();
       return userDao.AddNewFrined(addFriendEntity);
    }
}

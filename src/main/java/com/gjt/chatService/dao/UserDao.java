package com.gjt.chatService.dao;

import com.gjt.chatService.entity.UserEntity;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/12/0:11
 */
public interface UserDao {

    List<UserEntity> GetOnlineUser();
}

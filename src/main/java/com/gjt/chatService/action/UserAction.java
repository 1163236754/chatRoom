package com.gjt.chatService.action;

import com.gjt.chatService.entity.AddFriendEntity;
import com.gjt.chatService.entity.UserEntity;
import com.gjt.chatService.service.UserService;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 查看在线用户
 * @date 2018/6/11/18:29
 */
public class UserAction {
    /**
     * 获取在线人数信息,好友
     * @return
     */
    public List<UserEntity> GetOnlineInformation(String userName){
        UserService userService = new UserService();
        return userService.GetOnlineUsersById(userName);
    }
    /**
     * 新增好友
     * @param addFriendEntity
     * @return
     */
    public int AddFriend(AddFriendEntity addFriendEntity){
       UserService userService = new UserService();
       return userService.AddFriend(addFriendEntity);
    }
}

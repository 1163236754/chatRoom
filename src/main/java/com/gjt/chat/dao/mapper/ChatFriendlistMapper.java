package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatFriendlist;

public interface ChatFriendlistMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatFriendlist record);

    int insertSelective(ChatFriendlist record);

    ChatFriendlist selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatFriendlist record);

    int updateByPrimaryKey(ChatFriendlist record);
}
package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatUsergroupchat;

public interface ChatUsergroupchatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatUsergroupchat record);

    int insertSelective(ChatUsergroupchat record);

    ChatUsergroupchat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatUsergroupchat record);

    int updateByPrimaryKey(ChatUsergroupchat record);
}
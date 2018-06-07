package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatGroupChat;

public interface ChatGroupChatMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupChat record);

    int insertSelective(ChatGroupChat record);

    ChatGroupChat selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupChat record);

    int updateByPrimaryKey(ChatGroupChat record);
}
package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatGroupmessage;

public interface ChatGroupmessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatGroupmessage record);

    int insertSelective(ChatGroupmessage record);

    ChatGroupmessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatGroupmessage record);

    int updateByPrimaryKeyWithBLOBs(ChatGroupmessage record);

    int updateByPrimaryKey(ChatGroupmessage record);
}
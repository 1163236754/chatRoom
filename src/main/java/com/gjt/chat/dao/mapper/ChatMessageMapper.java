package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatMessage;

public interface ChatMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatMessage record);

    int insertSelective(ChatMessage record);

    ChatMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatMessage record);

    int updateByPrimaryKeyWithBLOBs(ChatMessage record);

    int updateByPrimaryKey(ChatMessage record);
}
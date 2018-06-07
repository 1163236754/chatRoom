package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatLoginMessage;

public interface ChatLoginMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatLoginMessage record);

    int insertSelective(ChatLoginMessage record);

    ChatLoginMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatLoginMessage record);

    int updateByPrimaryKey(ChatLoginMessage record);

    int getAllOnLine();

    ChatLoginMessage selectByStuId(String stuId);
}
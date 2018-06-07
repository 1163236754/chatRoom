package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatOfflineMessage;

public interface ChatOfflineMessageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatOfflineMessage record);

    int insertSelective(ChatOfflineMessage record);

    ChatOfflineMessage selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatOfflineMessage record);

    int updateByPrimaryKeyWithBLOBs(ChatOfflineMessage record);

    int updateByPrimaryKey(ChatOfflineMessage record);
}
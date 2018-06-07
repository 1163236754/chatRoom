package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatPermission;

public interface ChatPermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatPermission record);

    int insertSelective(ChatPermission record);

    ChatPermission selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatPermission record);

    int updateByPrimaryKey(ChatPermission record);
}
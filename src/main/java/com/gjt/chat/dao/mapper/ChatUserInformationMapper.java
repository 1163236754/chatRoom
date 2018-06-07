package com.gjt.chat.dao.mapper;

import com.gjt.chat.entity.ChatShowInfoList;
import com.gjt.chat.entity.ChatUserInformation;

import java.util.List;
import java.util.Map;

public interface ChatUserInformationMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ChatUserInformation record);

    int insertSelective(ChatUserInformation record);

    ChatUserInformation selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ChatUserInformation record);

    int updateByPrimaryKey(ChatUserInformation record);

    List<ChatShowInfoList> selectByPageSize(Map map);

    Integer getAllCount();
}
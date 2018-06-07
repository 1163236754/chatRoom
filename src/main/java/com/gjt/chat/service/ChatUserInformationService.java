package com.gjt.chat.service;

import com.gjt.chat.entity.ChatShowInfoList;
import com.gjt.chat.entity.ChatUserInformation;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/30/17:17
 */
@Component
public interface ChatUserInformationService {

    /**
     * 新增一条用户信息
     * @param chatUserInformation
     * @return
     */
    int AddNew(ChatUserInformation chatUserInformation);

    /**
     * 根据分页数据带条件查询
     * @param map
     * @return
     */
    List<ChatShowInfoList> getList(Map map);

    /**
     * 获取总是
     * @return
     */
    Integer getAllCount();

    /**
     * 根据id获取信息
     */
    ChatUserInformation selectById(Integer id);

    /**
     * 根据id更新
     * @param chatUserInformation
     * @return
     */
    int update(ChatUserInformation chatUserInformation);
}

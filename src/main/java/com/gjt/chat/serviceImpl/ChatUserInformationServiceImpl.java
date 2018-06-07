package com.gjt.chat.serviceImpl;

import com.gjt.chat.dao.mapper.ChatUserInformationMapper;
import com.gjt.chat.entity.ChatShowInfoList;
import com.gjt.chat.entity.ChatUserInformation;
import com.gjt.chat.service.ChatUserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/31/20:56
 */
@Component
public class ChatUserInformationServiceImpl implements ChatUserInformationService{

    @Autowired
    private ChatUserInformationMapper chatUserInformationMapper;

    /**
     * 新增一条信息
     * @param chatUserInformation
     * @return
     */
    @Override
    public int AddNew(ChatUserInformation chatUserInformation) {
        return this.chatUserInformationMapper.insert(chatUserInformation);
    }

    /**
     * 根据条件获取所有信息
     * @param map
     * @return
     */
    @Override
    public List<ChatShowInfoList> getList(Map map) {
        return this.chatUserInformationMapper.selectByPageSize(map);
    }

    /**
     * 获取总记录数
     * @return
     */
    @Override
    public Integer getAllCount() {
        return this.chatUserInformationMapper.getAllCount();
    }

    /**
     * 根据id查询指定消息
     * @param id
     * @return
     */
    @Override
    public ChatUserInformation selectById(Integer id) {
        return this.chatUserInformationMapper.selectByPrimaryKey(id);
    }

    /**
     * 根据id更新数据
     * @param chatUserInformation
     * @return
     */
    @Override
    public int update(ChatUserInformation chatUserInformation) {
        return this.chatUserInformationMapper.updateByPrimaryKey(chatUserInformation);
    }
}

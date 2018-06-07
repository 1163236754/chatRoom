package com.gjt.chat.serviceImpl;

import com.gjt.chat.dao.mapper.ChatGroupChatMapper;
import com.gjt.chat.entity.ChatGroupChat;
import com.gjt.chat.service.ChatGroupChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/30/15:42
 */
@Component
public class ChatGroupChatServiceImpl implements ChatGroupChatService{

    @Autowired
    private  ChatGroupChatMapper chatGroupChatMapper;

    /**
     * 根据Id查看qq群信息
     * @param id
     * @return
     */
    @Override
    public ChatGroupChat SelectPrimaryKey(Integer id) {
        return this.chatGroupChatMapper.selectByPrimaryKey(id);
    }
}

package com.gjt.chat.serviceImpl;

import com.gjt.chat.dao.mapper.ChatLoginMessageMapper;
import com.gjt.chat.entity.ChatLoginMessage;
import com.gjt.chat.service.ChatLoginMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/31/21:39
 */
@Component
public class ChatLoginMessageServiceImpl implements ChatLoginMessageService {

    @Autowired
    private ChatLoginMessageMapper chatLoginMessageMapper;

    /**
     * 插入一条注册信息
     * @param chatLoginMessage
     * @return
     */
    @Override
    public int addNew(ChatLoginMessage chatLoginMessage) {
        return this.chatLoginMessageMapper.insert(chatLoginMessage);
    }

    @Override
    public int update(ChatLoginMessage chatLoginMessage) {
        return 0;
    }

    /**
     * 获取所有当前在线人数
     * @return
     */
    @Override
    public int getOnLineNum() {
        return this.chatLoginMessageMapper.getAllOnLine();
    }

    @Override
    public ChatLoginMessage getLoginMessageById(String stuId) {
        return this.chatLoginMessageMapper.selectByStuId(stuId);
    }
}

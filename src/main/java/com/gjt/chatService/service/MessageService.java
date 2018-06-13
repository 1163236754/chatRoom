package com.gjt.chatService.service;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.dao.MessageDao;
import com.gjt.chatService.daoImpl.MessageDaoImpl;
import com.gjt.chatService.entity.GetEntity;
import com.gjt.chatService.entity.Message;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  消息处理service层
 * @date 2018/6/4/8:39
 */
public class MessageService {

    /**
     * 发送新消息
     * @param object
     * @return
     */
    public int MessageSendService(Message object){
        MessageDao messageDao = new MessageDaoImpl();
        return messageDao.InsertMessage(object);
    }

    /**
     * 发送群消息
     * @return
     */
    public int MessageSendGroupService(Message object){
        MessageDao messageDao = new MessageDaoImpl();
        return messageDao.InsertGroupMessage(object);
    }

    /**
     * 接收消息（个人消息）
     * @return
     * @param getEntity
     */
    public List<ChatMessage> MessageReciveService(GetEntity getEntity){
        MessageDao messageDao = new MessageDaoImpl();
        List<ChatMessage> list = messageDao.ReciveMessage(getEntity);
        return list;
    }

    /**
     * 接收群消息
     * @param getEntity
     * @return
     */
    public List<ChatGroupmessage> MessageReciveGroupAction(GetEntity getEntity){
        MessageDao messageDao = new MessageDaoImpl();
        List<ChatGroupmessage> list = messageDao.MessageReciveGroupAction(getEntity);
        return list;
    }
}

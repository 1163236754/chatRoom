package com.gjt.chatService.service;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.dao.MessageDao;
import com.gjt.chatService.daoImpl.MessageDaoImpl;
import com.gjt.chatService.entity.GetMessageEntity;
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
     * @param getMessageEntity
     */
    public List<ChatMessage> MessageReciveService(GetMessageEntity getMessageEntity){
        MessageDao messageDao = new MessageDaoImpl();
        List<ChatMessage> list = messageDao.ReciveMessage(getMessageEntity);
        return list;
    }

    /**
     * 接收群消息
     * @param getMessageEntity
     * @return
     */
    public List<ChatGroupmessage> MessageReciveGroupAction(GetMessageEntity getMessageEntity){
        MessageDao messageDao = new MessageDaoImpl();
        List<ChatGroupmessage> list = messageDao.MessageReciveGroupAction(getMessageEntity);
        return list;
    }
}

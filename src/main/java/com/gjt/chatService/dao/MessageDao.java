package com.gjt.chatService.dao;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.entity.GetEntity;
import com.gjt.chatService.entity.Message;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/4/8:42
 */
public interface MessageDao {

    /**
     * 发送消息
     * @param object
     * @return
     */
    int InsertMessage(Message object);

    /**
     * 接受消息
     * @param getEntity
     * @return
     */
    List<ChatMessage> ReciveMessage(GetEntity getEntity);

    /**
     * 接收群消息
     * @param getEntity
     * @return
     */
    List<ChatGroupmessage> MessageReciveGroupAction(GetEntity getEntity);

    /**
     * 插入群消息
     * @param getMessageEntity
     * @return
     */
    int InsertGroupMessage(Message getMessageEntity);
}

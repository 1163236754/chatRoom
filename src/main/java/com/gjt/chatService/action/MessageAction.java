package com.gjt.chatService.action;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.entity.GetMessageEntity;
import com.gjt.chatService.entity.Message;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.service.MessageService;

import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/3/21:20
 */
public class MessageAction {
    /**
     * 消息转发
     * @return
     * @param object
     */
    public int MessageSendAction(MessageEntity object){
        MessageService messageService = new MessageService();
        Message message = new Message();
        message.setSender(object.getSender());
        message.setReciver(object.getReciver());
        message.setContent(object.getContent());
        message.setSenderIp(object.getSenderIp());
        message.setGroupId(object.getGroupId());
        if(messageService.MessageSendService(message) == 1){
            System.out.println("发送成功");
            return 1;
        }
        System.err.println("发送失败");
        return 0;
    }
    /**
     * 群消息转发
     * @return
     * @param object
     */
    public int MessageSendGroupService(MessageEntity object){
        MessageService messageService = new MessageService();
        Message message = new Message();
        message.setSender(object.getSender());
        message.setReciver(object.getReciver());
        message.setContent(object.getContent());
        message.setSenderIp(object.getSenderIp());
        message.setGroupId(object.getGroupId());
        if(messageService.MessageSendGroupService(message) == 1){
            System.out.println("发送成功");
            return 1;
        }
        System.err.println("发送失败");
        return 0;
    }
    /**
     * 接收消息
     * @return
     */
    public List<ChatMessage> MessageReciveAction(GetMessageEntity getMessageEntity){
        MessageService messageService = new MessageService();
        List<ChatMessage> list = messageService.MessageReciveService(getMessageEntity);
        return list;
    }

    /**
     * 接收群消息
     * @param getMessageEntity
     * @return
     */
    public List<ChatGroupmessage> MessageReciveGroupAction(GetMessageEntity getMessageEntity){
        MessageService messageService = new MessageService();
        List<ChatGroupmessage> list = messageService.MessageReciveGroupAction(getMessageEntity);
        return list;
    }
}

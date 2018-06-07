package com.gjt.chat.service;

import com.gjt.chat.entity.ChatGroupChat;
import org.springframework.stereotype.Component;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/30/15:41
 */
@Component
public interface ChatGroupChatService {
    ChatGroupChat SelectPrimaryKey(Integer id);
}

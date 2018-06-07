package com.gjt.chat.service;

import com.gjt.chat.entity.ChatLoginMessage;
import org.springframework.stereotype.Component;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 登陆信息
 * @date 2018/5/31/21:38
 */
@Component
public interface ChatLoginMessageService {

    /**
     * 注册的时候插入的登陆信息
     * @return
     */
    int addNew(ChatLoginMessage chatLoginMessage);

    /**
     * 更新消息
     * @param chatLoginMessage
     * @return
     */
    int update(ChatLoginMessage chatLoginMessage);

    /**
     * 获取当前在线人数
     * @return
     */
    int getOnLineNum();

    /**
     * 根据学号查询登陆信息
     * @return
     */
    ChatLoginMessage getLoginMessageById(String stuId);

}

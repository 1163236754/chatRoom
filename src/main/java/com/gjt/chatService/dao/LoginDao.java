package com.gjt.chatService.dao;

import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  LoginDao层
 * @date 2018/6/3/9:42
 */
public interface LoginDao {
    /**
     * 密码验证
     * @return
     */
    Map<String,Object> VerificationLogin(String username);

    int ChangeStatus(String username);
}

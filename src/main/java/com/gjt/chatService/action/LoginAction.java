package com.gjt.chatService.action;

import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.service.LoginService;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  登陆验证Action层
 * @date 2018/6/1/21:23
 */

public class LoginAction {
    /**
     * 密码验证服务
     * @param userSetting
     * @return
     */
    public int validPassWord(LoginEntity userSetting){
        LoginService loginService = new LoginService();
        int result = loginService.Login(userSetting);
        return result;
    }
}

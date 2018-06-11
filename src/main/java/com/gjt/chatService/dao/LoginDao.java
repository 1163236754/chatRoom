package com.gjt.chatService.dao;

import com.gjt.chatService.entity.UserEntity;

import java.util.List;
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
    /**
     * 登陆成功之后修改对应用户名的登陆状态便于我们查找
     * @param username
     * @return
     */
    int ChangeStatus(String username);
    /**
     * 获取在线人数
     * @return
     */
    int getOnlineCounts();


}

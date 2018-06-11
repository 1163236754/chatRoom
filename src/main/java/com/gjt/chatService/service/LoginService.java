package com.gjt.chatService.service;

import com.gjt.chatService.dao.LoginDao;
import com.gjt.chatService.daoImpl.LoginDaoImpl;
import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.utils.MessageUtils;

import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: LoginService层
 * @date 2018/6/3/9:34
 */
public class LoginService {
    /**
     * 登陆验证处理service层
     * @param loginEntity
     * @return
     */
    public Map<String,Object> Login(LoginEntity loginEntity){
        LoginDao loginDao = new LoginDaoImpl();
        Map<String,Object> result = loginDao.VerificationLogin(loginEntity.getUserName());

        MessageUtils messageUtils = new MessageUtils();
        String password = messageUtils.decryptByBase64(result.get("password_encrypted").toString());
        if(password.equals(loginEntity.getPassword())&&(Integer.parseInt(result.get("is_login").toString()) == 0)){
            int changeStatus = loginDao.ChangeStatus(loginEntity.getUserName());
            if(changeStatus == 1){
                System.out.println("登陆成功!"+"状态:"+"已登陆");
            }else {
                System.out.println("发生错误!"+"状态:"+"登陆失败");
            }
            return result;
        }else {
            if(!password.equals(loginEntity.getPassword())){
                System.out.println("密码错误");
            }else if(Integer.parseInt(result.get("is_login").toString()) == 1){
                System.out.println("账号已登陆");
            }
            return null;
        }

    }
}

package com.gjt.chatService;

import com.gjt.chatService.dao.LoginDao;
import com.gjt.chatService.daoImpl.LoginDaoImpl;

import java.text.ParseException;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  测试类
 * @date 2018/6/3/17:29
 */
public class test {

    public static void main(String[] args) throws ParseException {
//        String userName = "11621380110";
//        String revicerName = userName.substring(0,9);
//        System.out.println(revicerName);
        LoginDao userDao = new LoginDaoImpl();
        userDao.VerificationLogin("11621380110");
    }
}

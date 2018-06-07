package com.gjt.chat.utils;

import java.util.Base64;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 加密工具类
 * @date 2018/5/30/18:38
 */
public class Encryption {

    /**
     * 加密工具
     */
    final Base64.Encoder encoder = Base64.getEncoder();

    /**
     * 加密方法
     * @param text
     * @return
     * @throws Exception
     */
    public String GetPassword(String text) throws Exception {
        byte[] textByte = text.getBytes("UTF-8");
        String encodedText = encoder.encodeToString(textByte);
        System.out.println("加密过后密码是：" + encodedText);
        return encodedText;
    }
}

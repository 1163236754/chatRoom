package com.gjt.chat.utils;

import java.util.Base64;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 解密工具类
 * @date 2018/5/30/18:39
 */
public class Decrypt {

    /**
     * 密钥
     */
    private String key = "ddd306ddd";
    /**
     * 解密工具
     */
    final Base64.Decoder decoder = Base64.getDecoder();

    /**
     * 解密方法
     * @param encodedText
     * @return
     * @throws Exception
     */
    public String GetPassword(String encodedText) throws Exception {
        String password = new String(decoder.decode(encodedText), "UTF-8");
        return password;
    }
}

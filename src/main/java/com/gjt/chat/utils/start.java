package com.gjt.chat.utils;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/5/31/20:38
 */
public class start {
    public static void main(String[] args) {
        /**
         * 加密
         */
        Encryption encryption = new Encryption();
        /**
         * 解密
         */
        Decrypt decrypt = new Decrypt();
        String result = null;
        try {
            String ul = encryption.GetPassword("ddd1111");
            result = decrypt.GetPassword(ul);
            System.out.println("解密之后：" + result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

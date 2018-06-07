package com.gjt.chatService.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 文件处理工具
 * @date 2018/5/13/15:15
 */
public class MessageUtils {
    /**
     * 将压缩成base64格式
     * @param Message
     * @return
     */
    public String encryptToBase64(String Message) {
        if (Message == null) {
            return null;
        }
        try {
            byte[] b = Message.getBytes();
            return Base64.getEncoder().encodeToString(b);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 将base64格式的消息转回正常格式
     * @param base64
     * @return
     */
    public String decryptByBase64(String base64) {
        if (base64 == null ) {
            return "消息转换失败。";
        }
        String result = null;
        try {
            result = new String(Base64.getDecoder().decode(base64),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  result;
    }
}

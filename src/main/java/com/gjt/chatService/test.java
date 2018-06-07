package com.gjt.chatService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  测试类
 * @date 2018/6/3/17:29
 */
public class test {

    public static void main(String[] args) throws ParseException {
        Date dt = new Date();
        System.out.println(dt);
        SimpleDateFormat sdf1= new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy", Locale.ENGLISH);
        SimpleDateFormat sdf2= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println(sdf2.format(dt));
    }
}

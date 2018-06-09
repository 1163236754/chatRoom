package com.gjt.chatService.action;

import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.entity.GetMessageEntity;
import com.gjt.chatService.entity.LoginEntity;
import com.gjt.chatService.entity.MessageEntity;
import com.gjt.chatService.entity.ResponseEntity;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  主Action负责分流请求
 * @date 2018/6/3/20:40
 */
public class MainAction {

    public List<ResponseEntity> DealWithAction(Object object) throws Exception{
        /**
         * 处理对象中的数值
         */
        List<ResponseEntity> result = new ArrayList<>();
        Class objectClass = object.getClass();
        Field[] fields = objectClass.getDeclaredFields();
        Map<String,String> type = new HashMap<>();
        fields[0].setAccessible(true);
        type.put(fields[0].getName(),fields[0].get(object).toString());
        if(type.get("type").equals("Login")){
            LoginAction loginAction = new LoginAction();
            int value = loginAction.validPassWord((LoginEntity) object);
            if(value == 1){
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("200");
                responseEntity.setResponseContent("登陆成功");
                result.add(responseEntity);
                return result;
            }else {
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("201");
                responseEntity.setResponseContent("登陆失败");
                result.add(responseEntity);
                result.add(responseEntity);
                return result;
            }
        }else if(type.get("type").equals("send")){
            MessageAction messageAction = new MessageAction();
            int value = messageAction.MessageSendAction((MessageEntity)object);
            if(value == 1){
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("200");
                responseEntity.setResponseContent("发送成功");
                result.add(responseEntity);
                return result;
            }else {
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("201");
                responseEntity.setResponseContent("发送失败");
                result.add(responseEntity);
                return result;
            }
        }else if(type.get("type").equals("request")){
            MessageAction messageAction = new MessageAction();
            List<ChatMessage> message = messageAction.MessageReciveAction((GetMessageEntity) object);
            if(message != null){
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("200");
                responseEntity.setResponseContent("接收成功");
                responseEntity.setMessagesData(message);
                result.add(responseEntity);
                return result;
            }else {
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("201");
                responseEntity.setResponseContent("接收失败");
                result.add(responseEntity);
                return result;
            }
        } else if(type.get("type").equals("sendGroup")){
            MessageAction messageAction = new MessageAction();
            int value = messageAction.MessageSendGroupService((MessageEntity)object);
            if(value == 1){
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("200");
                responseEntity.setResponseContent("发送成功");
                result.add(responseEntity);
                return result;
            }else {
                ResponseEntity responseEntity = new ResponseEntity();
                responseEntity.setStatusCode("201");
                responseEntity.setResponseContent("发送失败");
                result.add(responseEntity);
                return result;
            }
        }
        return null;
    }
}

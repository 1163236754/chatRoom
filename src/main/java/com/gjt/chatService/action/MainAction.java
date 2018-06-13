package com.gjt.chatService.action;

import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.entity.*;

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
        switch (type.get("type")){
            case "login":
                result = LoginAction(object);break;
            case "send":
                result = SendMessageAction(object);break;
            case "request":
                result = RequestMsgAction(object);break;
            case "sendGroup":
                result = SendGroupMsgAction(object);break;
            case "requestFriendList":
                result = RequestFriendList(object);break;
            case "addFriend":
                result = RequestAddNewFriends(object);break;
        }
        return result;
    }

    /**
     * 登陆处理
     * @param object
     * @return
     */
    private List<ResponseEntity> LoginAction(Object object){
        List<ResponseEntity> result = new ArrayList<>();
        LoginAction loginAction = new LoginAction();
        Map<String,Object> value = loginAction.validPassWord((LoginEntity) object);
        if(value != null){
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("200");
            responseEntity.setResponseContent("登陆成功");
            responseEntity.setLoginMessage(value);
            result.add(responseEntity);
            return result;
        }else {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("201");
            responseEntity.setResponseContent("登陆失败");
            result.add(responseEntity);
            return result;
        }
    }
    /**
     * 发送消息
     * @param object
     * @return
     */
    private List<ResponseEntity> SendMessageAction(Object object){
        List<ResponseEntity> result = new ArrayList<>();
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
    }
    /**
     * 请求消息（一对一消息）
     * @param object
     * @return
     */
    private List<ResponseEntity> RequestMsgAction(Object object){
        List<ResponseEntity> result = new ArrayList<>();
        MessageAction messageAction = new MessageAction();
        MessageEntity messageEntity = (MessageEntity) object;
        GetEntity getEntity = new GetEntity();
        getEntity.setIsLoginId(messageEntity.getSender());
        getEntity.setGroupId(messageEntity.getReciver());
        getEntity.setType(messageEntity.getType());
        List<ChatMessage> message = messageAction.MessageReciveAction(getEntity);
        if(message != null){
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("200");
            responseEntity.setResponseContent("success");
            responseEntity.setType("request");
            responseEntity.setMessagesData(message);
            result.add(responseEntity);
            return result;
        }else {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("201");
            responseEntity.setResponseContent("err");
            result.add(responseEntity);
            return result;
        }
    }
    /**
     * 发送群消息
     * @param object
     * @return
     */
    private List<ResponseEntity> SendGroupMsgAction(Object object){
        List<ResponseEntity> result = new ArrayList<>();
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
    /**
     * 请求在线好友列表
     * @param object
     * @return
     */
    private List<ResponseEntity> RequestFriendList(Object object){
        List<ResponseEntity> result = new ArrayList<>();
        UserAction userAction = new UserAction();
        MessageEntity messageEntity = (MessageEntity) object;
        GetEntity getEntity = new GetEntity();
        // 设置登陆账号
        getEntity.setIsLoginId(messageEntity.getSender());
        // 设置请求类型
        getEntity.setType("requestFriendList");
        List<UserEntity> userList = userAction.GetOnlineInformation(getEntity.getIsLoginId());
        if(userList != null){
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("200");
            responseEntity.setResponseContent("success");
            responseEntity.setType("requestFriendList");
            responseEntity.setUserEntities(userList);
            result.add(responseEntity);
            return result;
        }else {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("201");
            responseEntity.setResponseContent("err");
            result.add(responseEntity);
            return result;
        }
    }
    /**
     * 新增好友
     * @param object
     * @return
     */
    private List<ResponseEntity> RequestAddNewFriends(Object object){
        List<ResponseEntity> result = new ArrayList<>();
        UserAction userAction = new UserAction();
        MessageEntity messageEntity = (MessageEntity) object;
        AddFriendEntity addFriendEntity = new AddFriendEntity();
        addFriendEntity.setFriendId(messageEntity.getReciver());
        addFriendEntity.setUserId(messageEntity.getSender());
        addFriendEntity.setType("addFriend");
        int value = userAction.AddFriend(addFriendEntity);
        if(value == 1){
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("200");
            responseEntity.setResponseContent("sucess");
            responseEntity.setType("addFriend");
            result.add(responseEntity);
            return result;
        }else {
            ResponseEntity responseEntity = new ResponseEntity();
            responseEntity.setStatusCode("201");
            responseEntity.setResponseContent("err");
            result.add(responseEntity);
            return result;
        }
    }

}

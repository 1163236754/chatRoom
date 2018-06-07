package com.gjt.chat.api;

import com.gjt.chat.entity.ChatLoginMessage;
import com.gjt.chat.entity.ChatUserInformation;
import com.gjt.chat.service.ChatLoginMessageService;
import com.gjt.chat.service.ChatUserInformationService;
import com.gjt.chat.utils.Encryption;
import com.gjt.chat.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author 官江涛
 * @version 1.0
 * @Title: 注册接口
 * @date 2018/5/30/16:10
 */
@RestController
@RequestMapping("/register")
@CrossOrigin
public class RegisterCountroller {

    /**
     * 个人信息写入
     */
    @Autowired
    private ChatUserInformationService chatUserInformationService;

    @Autowired
    private ChatLoginMessageService chatLoginMessageService;

    /**
     * 注册
     * @param chatUserInformation
     * @return
     */
    @RequestMapping(value = "/addNew", method = RequestMethod.POST)
    public ResponseEntity<Json> Register(@RequestBody ChatUserInformation chatUserInformation){
        Json jsonResult = new Json();
        Encryption encryption = new Encryption();
        String password = null;
        ChatLoginMessage chatLoginMessage = new ChatLoginMessage();
        try {
            password =  encryption.GetPassword(chatUserInformation.getPasswordEncrypted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        chatUserInformation.setPasswordEncrypted(password);
        /**
         * 注册默认不登陆
         */
        chatLoginMessage.setIsLogin(0);
        chatLoginMessage.setPasswordEncrypted(password);
        chatLoginMessage.setUsername(chatUserInformation.getStuId());

        int resultM = chatLoginMessageService.addNew(chatLoginMessage);
        int result =  chatUserInformationService.AddNew(chatUserInformation);
        if(result != 0){
            jsonResult.setStatus("success");
            jsonResult.setMessage("注册成功");
        }else {
            jsonResult.setStatus("err");
            jsonResult.setMessage("注册失败");
        }
        return ResponseEntity.ok(jsonResult);
    }
}

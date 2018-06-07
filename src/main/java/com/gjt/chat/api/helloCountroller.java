//package com.gjt.chat;
//
//import com.gjt.chat.entity.ChatGroupChat;
//import com.gjt.chat.service.ChatGroupChatService;
//import com.utils.JsonResult.Json;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * @author 官江涛
// * @version 1.0
// * @Title:
// * @date 2018/5/30/8:49
// */
//@RestController
//@RequestMapping("/test")
//@CrossOrigin
//public class helloCountroller {
//
//    @Autowired
//    private  ChatGroupChatService chatGroupChatService;
//
//    @RequestMapping(value = "/hello/{id}",method = RequestMethod.GET)
//    public String say(@RequestParam(value = "id", required = false) Integer id){
//      return "hello!Springboot!"+"age:"+id;
//    }
//
//    @RequestMapping(value = "/test",method = RequestMethod.GET)
//    public ResponseEntity<Json> result(){
//        Json r = new Json();
//        ChatGroupChat chatGroupChat = chatGroupChatService.SelectPrimaryKey(1);
//        r.setResult(chatGroupChat);
//        return ResponseEntity.ok(r);
//    }
//}

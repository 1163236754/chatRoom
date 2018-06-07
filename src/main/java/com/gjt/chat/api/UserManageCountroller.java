package com.gjt.chat.api;

import com.gjt.chat.entity.*;
import com.gjt.chat.service.ChatLoginMessageService;
import com.gjt.chat.service.ChatUserInformationService;
import com.gjt.chat.utils.Encryption;
import com.gjt.chat.utils.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  展示所有用户
 * @date 2018/5/31/22:07
 */
@RestController
@RequestMapping("/userInformation")
@CrossOrigin
public class UserManageCountroller {
    /**
     * 个人信息写入
     */
    @Autowired
    private ChatUserInformationService chatUserInformationService;
    @Autowired
    private ChatLoginMessageService chatLoginMessageService;
    /**
     * 查询所有
     * @param chatGetAll
     * @return
     */
    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<Json> GetAll(@Valid ChatGetAll chatGetAll){
        Json jsonResult = new Json();
        java.util.Map keys = new HashMap<>();
        // 拼装查询
        keys.put("page",Integer.parseInt(chatGetAll.getPage()));
        keys.put("pageSize",Integer.parseInt(chatGetAll.getPageSize()));
        if(chatGetAll.getClassId() == ""){
            keys.put("classId",null);
        } else {
            keys.put("classId","%"+chatGetAll.getClassId()+"%");
        }
        if(chatGetAll.getStuId() == ""){
            keys.put("stuId",null);
        }else {
            keys.put("stuId","%"+chatGetAll.getStuId()+"%");
        }
        // 获取到查询的信息
        List<ChatShowInfoList> result =  chatUserInformationService.getList(keys);
        //获取总数
        Integer count = chatUserInformationService.getAllCount();
        // 处理关于是否在线的函数
        if(result != null){
            for (int i = 0; i < result.size(); i++) {
               String status =  result.get(i).getIsOnline();
               if(Integer.parseInt(status) == 1){
                   result.get(i).setIsOnline("是");
               }else {
                   result.get(i).setIsOnline("否");
               }
            }
            jsonResult.setStatus("success");
            jsonResult.setMessage("查询成功");
            // 总数
            jsonResult.setTotalCount(count);
            // 返回结果
            jsonResult.setResult(result);
        }else {
            jsonResult.setStatus("err");
            jsonResult.setMessage("查询失败");
        }
        return ResponseEntity.ok(jsonResult);
    }

    /**
     * 根据id查询指定消息
     * @param GetByIdDTO
     * @return
     */
    @RequestMapping(value = "/byPrimaryKey", method = RequestMethod.GET)
    public ResponseEntity<Json> SelectById(@Valid GetByIdDTO getByIdDTO){
        Json jsonResult = new Json();
        ChatUserInformation chatUserInformation = new ChatUserInformation();
        chatUserInformation = chatUserInformationService.selectById(getByIdDTO.getId());
        if(chatUserInformation != null){
            jsonResult.setStatus("success");
            jsonResult.setMessage("查询成功");
            // 返回结果
            jsonResult.setResult(chatUserInformation);
        }else {
            jsonResult.setStatus("err");
            jsonResult.setMessage("查询失败");
        }
        return ResponseEntity.ok(jsonResult);
    }

    /**
     * 根据id修改信息
     * @param chatUserInformation
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public ResponseEntity<Json> UpdateById(@RequestBody ChatUserInformation chatUserInformation){
        // 暂时不提供密码修改功能
        Json jsonResult = new Json();
        Encryption encryption = new Encryption();
        String password = null;
        try {
            password =  encryption.GetPassword(chatUserInformation.getPasswordEncrypted());
        } catch (Exception e) {
            e.printStackTrace();
        }
        chatUserInformation.setPasswordEncrypted(password);
        int result =  chatUserInformationService.AddNew(chatUserInformation);
        if(result != 0){
            jsonResult.setStatus("success");
            jsonResult.setMessage("修改成功");
        }else {
            jsonResult.setStatus("err");
            jsonResult.setMessage("注册失败");
        }
        return ResponseEntity.ok(jsonResult);
    }

    @RequestMapping(value = "/getOnlineNo", method = RequestMethod.GET)
    public ResponseEntity<Json> UpdateById(@Valid GetByIdDTO getByIdDTO){
        Json jsonResult = new Json();
        int result =  chatLoginMessageService.getOnLineNum();
        if(result >= 0){
            jsonResult.setStatus("success");
            jsonResult.setTotalCount(result);
        }else {
            jsonResult.setStatus("err");
            jsonResult.setMessage("注册失败");
        }
        return ResponseEntity.ok(jsonResult);
    }
}

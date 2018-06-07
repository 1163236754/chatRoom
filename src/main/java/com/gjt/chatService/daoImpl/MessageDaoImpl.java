package com.gjt.chatService.daoImpl;

import com.gjt.chat.entity.ChatGroupmessage;
import com.gjt.chat.entity.ChatMessage;
import com.gjt.chatService.dao.MessageDao;
import com.gjt.chatService.entity.*;
import com.gjt.chatService.utils.KeyWord;
import com.gjt.chatService.utils.Sql;
import com.gjt.chatService.utils.SqlUtils;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  消息处理实现层
 * @date 2018/6/4/8:43
 */
public class MessageDaoImpl implements MessageDao{

    /**
     * 初始化链接状态
     */
    private Connection conn = null;
    /**
     * 加载数据库查询组件
     */
    SqlUtils sqlUtils = new SqlUtils();
    /**
     * 一对一消息插入
     * @param object
     * @return
     */
    @Override
    public int InsertMessage(Message object) {
        SqlEntity sqlEntity = new SqlEntity();
        // 设置表名称
        sqlEntity.setTableName(KeyWord.DB_message);
        // 解析对象
        sqlEntity.setObject(object);
        // 启动SQL生成工具
        Sql baseSQL = new Sql(sqlEntity);
        String sql = null;
        try {
            // 生成sql
            sql = baseSQL.BaseValueSQL();
            System.out.println("生成的SQL语句是"+sql);
        } catch (Exception e) {
            System.out.println("sql语句有问题，请检查：" + sql);
            e.printStackTrace();
        }
        int resultMessage = sqlUtils.Update(sql);
        sqlEntity.setTableName(KeyWord.DB_chat_offlinemessage);
        try {
            sql = baseSQL.BaseValueSQL();
            System.out.println("生成的SQL语句是"+sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
        int InsertMessage =  sqlUtils.Update(sql);
        if(resultMessage == 1&& InsertMessage == 1){
            return 1;
        }
        return 0;
    }
    /**
     * 接受消息
     * @param getMessageEntity
     * @return
     */
    @Override
    public List<ChatMessage> ReciveMessage(GetMessageEntity getMessageEntity) {
        // 启动工具
        SqlEntity sqlEntity = new SqlEntity();
        // 设置where条件的Id
        sqlEntity.setId("reciver");
        // 设置表
        sqlEntity.setTableName(KeyWord.DB_chat_offlinemessage);
        // 设置where条件的内容
        sqlEntity.setContent("'"+getMessageEntity.getIsLoginId()+"'");
        // 启动sql生成工具
        Sql baseSQL = new Sql(sqlEntity);
        String sql = null;
        try {
            // 生成sql
            sql = baseSQL.BaseSelectSQL() + baseSQL.BaseWhereSQL();
            // 输出sql便于检查错误
            System.out.println("生成的SQL语句是"+sql);
        } catch (Exception e) {
            System.out.println("sql语句有问题，请检查：" + sql);
            e.printStackTrace();
        }
         // 将结果从map ---> bean
        List<Map<String,Object>> result = sqlUtils.QueryList(sql);
        List<ChatMessage> resultValue = new ArrayList<>();
        if(result != null){
            for (int i = 0; i < result.size(); i++) {
                try {
                    ChatMessage chatMessage = new ChatMessage();
                    BeanUtils.populate(chatMessage,result.get(i));
                    resultValue.add(chatMessage);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
            // 删除数据库中对应数据
            sqlEntity.setId("reciver");
            sqlEntity.setTableName(KeyWord.DB_chat_offlinemessage);
            sqlEntity.setContent("'"+getMessageEntity.getIsLoginId()+"'");
            try {
                sql = baseSQL.BaseDeleteSQL() + baseSQL.BaseWhereSQL();
                System.out.println("生成的SQL语句是："+sql);
            } catch (Exception e) {
                System.out.println("sql语句有问题，请检查：" + sql);
                e.printStackTrace();
            }
            int i = sqlUtils.Update(sql);
            if(i == 1){
                System.out.println("删除成功");
            }else {
                System.err.println("删除失败");
            }
        }

        return resultValue;
    }
    /**
     *  查看群历史消息
     * @param getMessageEntity
     * @return
     */
    @Override
    public List<ChatGroupmessage> MessageReciveGroupAction(GetMessageEntity getMessageEntity) {
        // 启动工具
        SqlEntity sqlEntity = new SqlEntity();
        // 设置where条件的Id
        sqlEntity.setId("groupNumber");
        sqlEntity.setContent("'"+getMessageEntity.getGroupId()+"'");
        Sql baseSQL = new Sql(sqlEntity);
        // 设置sql语句
        String sql = "SELECT cuf.name,cg.content,cg.send_time FROM `chat_offlinegroupmessage` cg LEFT JOIN chat_userinformation cuf ON cg.sender = cuf.stu_id" + baseSQL.BaseWhereSQL();
        System.out.println("生成的SQL语句是："+sql);
        List<Map<String,Object>> result = sqlUtils.QueryList(sql);
        List<ChatGroupmessage> resultValue = new ArrayList<>();
        if(result != null){
            for (int i = 0; i < result.size(); i++) {
                try {
                    ChatGroupmessage chatGroupmessage = new ChatGroupmessage();
                    chatGroupmessage.setSender(result.get(i).get("name").toString());
                    chatGroupmessage.setContent(result.get(i).get("content").toString());
                    chatGroupmessage.setSendTime((Date) result.get(i).get("send_time"));
                    resultValue.add(chatGroupmessage);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("查询成功");
        }else {
            System.err.println("查询失败，已经没有未读消息了！");
        }
        return resultValue;
    }

    /**
     * 发送群消息
     * @param getMessageEntity
     * @return
     */
    @Override
    public int InsertGroupMessage(Message getMessageEntity) {
        // 解决实体字段和表字段不对应的问题
        GroupMessage groupMessage = new GroupMessage();
        groupMessage.setSender(getMessageEntity.getSender());
        groupMessage.setContent(getMessageEntity.getContent());
        groupMessage.setGroupNumber(getMessageEntity.getGroupId());
        groupMessage.setSendIp(getMessageEntity.getSenderIp());

        SqlEntity sqlEntity = new SqlEntity();
        // 设置表名称
        sqlEntity.setTableName(KeyWord.DB_chat_groupmessage);
        // 解析对象
        sqlEntity.setObject(groupMessage);
        // 启动SQL生成工具
        Sql baseSQL = new Sql(sqlEntity);
        String sql = null;
        try {
            // 生成sql
            sql = baseSQL.BaseValueSQL();
            System.out.println("群消息记录表，生成的SQL语句是"+sql);
        } catch (Exception e) {
            System.out.println("sql语句有问题，请检查：" + sql);
            e.printStackTrace();
        }
        int InsertMessage = sqlUtils.Update(sql);
        if(InsertMessage == 1){
            return 1;
        }
        return 0;
    }
}

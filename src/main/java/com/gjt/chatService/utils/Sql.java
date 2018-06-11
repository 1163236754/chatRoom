package com.gjt.chatService.utils;

import com.gjt.chatService.entity.SqlEntity;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  sql语句
 * @date 2018/6/3/10:03
 */
public class Sql {

    private SqlEntity sqlEntity;

    public Sql(SqlEntity sqlEntity){
        this.sqlEntity = sqlEntity;
    }

    /**
     * 基础查询sql语句封装
     */
    public String BaseSelectSQL(){
        String baseSql  = "select "+ sqlEntity.getSqlContent()+" from "+sqlEntity.getTableName();
        return baseSql;
    }
    /**
     * 封带条件 where
     * 单一条件查询
     */
    public String BaseWhereSQL(){
        String baseSql = null;
        baseSql  = " Where "+ sqlEntity.getId()+" = "+sqlEntity.getContent();
        return baseSql;
    }

    /**
     * 封带条件where
     * 支持多条件查询
     */
    public String BaseWhereMultiSQL(String connectives){
        Map<String,Object> keys = sqlEntity.getIdList();
        /**
         * 解析map获取到其中的key;
         * 使用迭代器，获取key;
         */
        List<String> keyList = new ArrayList<>();
        Iterator<String> iter = keys.keySet().iterator();
        while(iter.hasNext()){
            String key=iter.next();
            keyList.add(key);
        }
        String baseSql = " Where ";
        String sql = null;
        for (int i = 0; i < keyList.size(); i++) {
            if(i == 0){
                sql =  keyList.get(i)+" = "+keys.get(keyList.get(i));
            }
            if(i>0){
                sql +=  connectives + keyList.get(i)+" = "+keys.get(keyList.get(i));
            }
        }
        return baseSql+sql;
    }

    /**
     * 删除基础命令
     * 需要结合封装的where一起使用
     * @return
     */
    public String BaseDeleteSQL(){
        String baseSql  = "delete from "+sqlEntity.getTableName();
        return baseSql;
    }

    /**
     * 基本新增语法
     * 只提供一次性新增所有的功能
     * @return
     */
    public String BaseInsertSQL(){
        String baseSql  = null;
        try {
            baseSql = "INSERT INTO " + sqlEntity.getTableName() + " VALUES " + sqlEntity.geObjectValueBySQL();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseSql;
    }

    /**
     * 基础value赋值SQL
     * @return
     */
    public String BaseValueSQL(){
        String baseSql  = null;
        try {
            baseSql = "INSERT INTO " + sqlEntity.getTableName()+"(" + sqlEntity.getObjectBySQL() +")"+ " VALUES " + sqlEntity.geObjectValueBySQL();
            return baseSql;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * 基础更新SQL id用于查询指定sql
     * 需要结合where使用
     * @return
     */
    public String BaseUpdateSQL(){
        String baseSQL = null;
        try {
            baseSQL = "UPDATE " + sqlEntity.getTableName() + " SET " + sqlEntity.getUpdateSql();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseSQL;
    }

    /**
     * 自定义更新查询
     * @return
     */
    public String BaseUpdate(){
        String baseSQL = null;
        try {
            baseSQL = "UPDATE " + sqlEntity.getTableName() + " SET ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseSQL;
    }

    /**
     * 获取基础代码
     * @return
     */
    public String BaseGetAllCounts(){
        String baseSQL = null;
        try {
            baseSQL = "SELECT count(1) as counts FROM " + sqlEntity.getTableName() + " ";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baseSQL;
    }
}

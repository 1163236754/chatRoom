package com.gjt.chatService.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 官江涛
 * @version 1.0
 * @Title:  数据库基本查询工具
 * @date 2018/6/3/9:47
 */
public class SqlUtils {

    private Connection conn = null;

    /**
     * 查询所有数据
     * @param sql
     * @return
     */
    public Map<String,Object> Query(String sql){
        conn = DButils.open();
        Statement stmt;
        Map<String,Object> result = new HashMap<>();
        try {
            stmt = conn.createStatement();
            ResultSet resultSet;
            resultSet = stmt.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int col_len = metaData.getColumnCount();
            while(resultSet.next()){
                for(int i=0; i<col_len; i++ ){
                    String cols_name = metaData.getColumnName(i+1);
                    Object cols_value = resultSet.getObject(cols_name);
                    if(cols_value == null){
                        cols_value = "";
                    }
                    result.put(cols_name, cols_value);
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 更新数据
     * @param sql
     * @return
     */
    public int Update(String sql){
        conn = DButils.open();
        Statement stmt;
        int result = 0;
        try {
            stmt = conn.createStatement();
            result = stmt.executeUpdate(sql);
        }catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 集合查询
     * @param sql
     * @return
     */
    public List<Map<String,Object>> QueryList(String sql){
        conn = DButils.open();
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        Statement stmt = null;
        Map<String,Object> result = new HashMap<>();
        ResultSet resultSet  = null;
        try {
            stmt = conn.createStatement();
            resultSet = stmt.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int cols_len = metaData.getColumnCount();
            while(resultSet.next()){
                Map<String, Object> map = new HashMap<String, Object>();
                for(int i=0; i<cols_len; i++){
                    String cols_name = metaData.getColumnName(i+1);
                    Object cols_value = resultSet.getObject(cols_name);
                    if(cols_value == null){
                        cols_value = "";
                    }
                    map.put(cols_name, cols_value);
                }
                list.add(map);
            }
        } catch (Exception e){
            e.printStackTrace();
        } finally{
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

}

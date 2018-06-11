package com.gjt.chatService.daoImpl;

import com.gjt.chatService.dao.LoginDao;
import com.gjt.chatService.entity.SqlEntity;
import com.gjt.chatService.utils.KeyWord;
import com.gjt.chatService.utils.Sql;
import com.gjt.chatService.utils.SqlUtils;

import java.util.Map;


/**
 * @author 官江涛
 * @version 1.0
 * @Title:
 * @date 2018/6/3/9:43
 */
public class LoginDaoImpl implements LoginDao{

    /**
     * 加载数据库查询组件
     */
    SqlUtils sqlUtils = new SqlUtils();

    /**
     * 密码验证
     * @return
     */
    @Override
    public Map<String,Object> VerificationLogin(String username) {
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setTableName(KeyWord.DB_chat_loginmessage);
        sqlEntity.setId("username");
        sqlEntity.setContent(username);
        Sql baseSQL = new Sql(sqlEntity);
        String sql = baseSQL.BaseSelectSQL();
        return sqlUtils.Query(sql);
    }

    /**
     * 验证通过之后修改登陆状态
     * @return
     */
    @Override
    public int ChangeStatus(String username) {
        SqlEntity sqlEntity = new SqlEntity();
        /**
         * 设置表名称
         */
        sqlEntity.setTableName(KeyWord.DB_chat_loginmessage);
        sqlEntity.setId("username");
        sqlEntity.setContent(username);
        Sql baseSQL = new Sql(sqlEntity);
        String sql = baseSQL.BaseUpdate() + "is_login = 1 " +baseSQL.BaseWhereSQL();
        return sqlUtils.Update(sql);
    }
    /**
     * 获取总的在线人数
     * @return
     */
    @Override
    public int getOnlineCounts() {
        SqlEntity sqlEntity = new SqlEntity();
        sqlEntity.setTableName(KeyWord.DB_chat_loginmessage);
        sqlEntity.setId("is_login");
        sqlEntity.setContent("1");
        Sql baseSQL = new Sql(sqlEntity);
        String sql = baseSQL.BaseGetAllCounts() + baseSQL.BaseWhereSQL();
        return 0;
    }

}

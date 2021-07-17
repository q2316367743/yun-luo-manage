package xyz.esion.manage;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库测试
 *
 * @author Esion
 * @since 2021/7/17
 */
public class DatabaseTest {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/blog?serverTimezone=UTC", "root", "123456");
        DatabaseMetaData metaData = connection.getMetaData();
        Console.log(JSONUtil.toJsonStr(metaData));
    }

}

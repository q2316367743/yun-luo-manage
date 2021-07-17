package xyz.esion.manage;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;

import java.sql.*;

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
        System.out.println("数据库已知的用户: " + metaData.getUserName());
        System.out.println("数据库的系统函数的逗号分隔列表: " + metaData.getSystemFunctions());
        System.out.println("数据库的时间和日期函数的逗号分隔列表: " + metaData.getTimeDateFunctions());
        System.out.println("数据库的字符串函数的逗号分隔列表: " + metaData.getStringFunctions());
        System.out.println("数据库供应商用于 'schema' 的首选术语: " + metaData.getSchemaTerm());
        System.out.println("数据库URL: " + metaData.getURL());
        System.out.println("是否允许只读:" + metaData.isReadOnly());
        System.out.println("数据库的产品名称:" + metaData.getDatabaseProductName());
        System.out.println("数据库的版本:" + metaData.getDatabaseProductVersion());
        System.out.println("驱动程序的名称:" + metaData.getDriverName());
        System.out.println("驱动程序的版本:" + metaData.getDriverVersion());
        System.out.println("数据库中使用的表类型");
        ResultSet rs = metaData.getTableTypes();
        while (rs.next()) {
            System.out.println(rs.getString("TABLE_TYPE"));
        }
        System.out.println("数据库中使用的表");
        ResultSet rs1 = metaData.getTables(null, null, "%", new String[] { "TABLE" });
        //结果集的元数据
        ResultSetMetaData rsmd = rs1.getMetaData();
        for (int i = 1 ; i <= rsmd.getColumnCount(); i++ ){
            String column = rsmd.getColumnName(i);
            System.out.println(column);
        }
    }

}

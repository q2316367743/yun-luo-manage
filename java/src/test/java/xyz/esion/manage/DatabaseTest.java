package xyz.esion.manage;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import xyz.esion.manage.model.database.Field;
import xyz.esion.manage.model.database.Table;
import xyz.esion.manage.model.database.View;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 数据库测试
 *
 * @author Esion
 * @since 2021/7/17
 */
public class DatabaseTest {

    private static final String DATABASE = "pa";
    private static final String URL = "jdbc:mysql://192.168.1.101:3306/pa?serverTimezone=UTC";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456";

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        test(connection);
//        Database4MySql database4MySql = DatabaseUtil.initMySqlInformation(connection);
//        Console.log(JSONUtil.parseObj(database4MySql).toJSONString(4));
    }

    public static void test(Connection connection) throws SQLException {
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
        System.out.println("数据库中所有的表：");
        Statement tableStatement = connection.createStatement();
        ResultSet tablesResult = tableStatement.executeQuery("show table status");
        List<Table> tables = new ArrayList<>();
        while (tablesResult.next()){
            String comment = tablesResult.getString(Table.COLUMNS[17]);
            if (comment.equals("VIEW")){
                continue;
            }
            Table table = new Table();
            table.setName(tablesResult.getString(Table.COLUMNS[0]));
            table.setEngine(tablesResult.getString(Table.COLUMNS[1]));
            table.setVersion(tablesResult.getInt(Table.COLUMNS[2]));
            table.setRowFormat(tablesResult.getString(Table.COLUMNS[3]));
            table.setRows(tablesResult.getInt(Table.COLUMNS[4]));
            table.setAvgRowLength(tablesResult.getInt(Table.COLUMNS[5]));
            table.setDataLength(tablesResult.getInt(Table.COLUMNS[6]));
            table.setMaxDataLength(tablesResult.getInt(Table.COLUMNS[7]));
            table.setIndexLength(tablesResult.getInt(Table.COLUMNS[8]));
            table.setDataFree(tablesResult.getInt(Table.COLUMNS[9]));
            table.setAutoIncrement(tablesResult.getInt(Table.COLUMNS[10]));
            table.setCreateTime(tablesResult.getDate(Table.COLUMNS[11]));
            table.setUpdateTime(tablesResult.getDate(Table.COLUMNS[12]));
            table.setCheckTime(tablesResult.getDate(Table.COLUMNS[13]));
            table.setCollation(tablesResult.getString(Table.COLUMNS[14]));
            table.setCheckSum(tablesResult.getString(Table.COLUMNS[15]));
            table.setCreateOptions(tablesResult.getString(Table.COLUMNS[16]));
            table.setComment(comment);
            tables.add(table);
        }
        Console.log(JSONUtil.parseArray(tables).toJSONString(4));
        Console.log("数据库中所有的视图");
        Statement viewStatement = connection.createStatement();
        ResultSet viewResult = viewStatement.executeQuery("select * from information_schema.VIEWS;");
        List<View> views = new ArrayList<>();
        while (viewResult.next()){
            String schema = viewResult.getString(View.COLUMN[0]);
            if (schema.equals(DATABASE)){
                View view = new View();
                view.setName(viewResult.getString(View.COLUMN[1]));
                view.setDefinition(viewResult.getString(View.COLUMN[2]));
                view.setCheckOption(viewResult.getString(View.COLUMN[3]));
                view.setIsUpdatable(viewResult.getString(View.COLUMN[4]));
                view.setSecurityType(viewResult.getString(View.COLUMN[5]));
                view.setCharacterSetClient(viewResult.getString(View.COLUMN[6]));
                view.setCollationConnection(viewResult.getString(View.COLUMN[7]));
                views.add(view);
            }
        }
        Console.log(JSONUtil.parseArray(views).toJSONString(4));
        Console.log("数据库中表t_article字段：");
        Statement fieldStatement = connection.createStatement();
        ResultSet fieldResult = fieldStatement.executeQuery("SHOW FULL COLUMNS FROM t_article");
        List<Field> fields = new ArrayList<>();
        while (fieldResult.next()){
            Field field = new Field();
            field.setName(fieldResult.getString(Field.COLUMN[0]));
            field.setType(fieldResult.getString(Field.COLUMN[1]));
            field.setCollation(fieldResult.getString(Field.COLUMN[2]));
            field.setIsNull(fieldResult.getString(Field.COLUMN[3]));
            field.setKey(fieldResult.getString(Field.COLUMN[4]));
            field.setDefaultValue(fieldResult.getString(Field.COLUMN[5]));
            field.setExtra(fieldResult.getString(Field.COLUMN[6]));
            field.setPrivileges(fieldResult.getString(Field.COLUMN[7]));
            field.setComment(fieldResult.getString(Field.COLUMN[8]));
            fields.add(field);
        }
        Console.log(JSONUtil.parseArray(fields).toJSONString(4));
        connection.close();
    }

}

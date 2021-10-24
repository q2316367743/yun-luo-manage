package xyz.esion.manage.util;

import cn.hutool.core.util.StrUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.global.Constant;
import xyz.esion.manage.model.database.DatabaseInfo;
import xyz.esion.manage.model.database.Field;
import xyz.esion.manage.model.database.Table;
import xyz.esion.manage.model.database.View;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * 生成数据库url
 *
 * @author Esion
 * @since 2021/7/18
 */
public class DatabaseUtil {

//    private final static String QUERY_DATABASE_NAME = "select database() as name;";
    private final static String QUERY_TABLE = "show table status";
    private final static String QUERY_VIEW = "select * from information_schema.VIEWS";
    private final static String QUERY_FIELD = "SHOW FULL COLUMNS FROM ";

    /**
     * 获取数据源
     *
     * @param database 数据库信息
     * @return 数据源，如果连接失败返回null
     */
    public static DataSource getDataSource(Database database) {
        HikariConfig config = new HikariConfig();
        config.setUsername(database.getUsername());
        config.setPassword(database.getPassword());
        config.setDriverClassName(Constant.MYSQL_DRIVER);
        config.setJdbcUrl(StrUtil.format("jdbc:mysql://{}:{}/{}?serverTimezone=Asia/Shanghai&{}",
                database.getHost(),
                database.getPort(),
                database.getName(),
                database.getParameter()));
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        return new HikariDataSource(config);
    }

    /**
     * 初始化MySQL数据库信息
     */
    public static DatabaseInfo initMySqlInformation(Connection connection) throws SQLException {
        DatabaseInfo info = new DatabaseInfo();
        // 查询数据库名称
        Statement dbStatement = connection.createStatement();
        ResultSet dbResult = dbStatement.executeQuery(QUERY_TABLE);
        dbResult.next();
        String databaseName = dbResult.getString(1);
        info.setName(databaseName);
        // 查询全部的表
        Statement tableStatement = connection.createStatement();
        ResultSet tablesResult = tableStatement.executeQuery(QUERY_TABLE);
        List<Table> tables = new ArrayList<>();
        while (tablesResult.next()) {
            String comment = tablesResult.getString(Table.COLUMNS[17]);
            if ("VIEW".equals(comment)) {
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
            // 查询字段
            Statement fieldStatement = connection.createStatement();
            ResultSet fieldResult = fieldStatement.executeQuery(QUERY_FIELD + table.getName());
            List<Field> fields = new ArrayList<>();
            while (fieldResult.next()) {
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
            table.setFields(fields);
            tables.add(table);
        }
        info.setTables(tables);
        // 查询全部的视图
        Statement viewStatement = connection.createStatement();
        ResultSet viewResult = viewStatement.executeQuery(QUERY_VIEW);
        List<View> views = new ArrayList<>();
        while (viewResult.next()) {
            String schema = viewResult.getString(View.COLUMN[0]);
            if (schema.equals(databaseName)) {
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
        info.setViews(views);
        connection.close();
        return info;
    }

}

package xyz.esion.manage.util;

import cn.hutool.core.lang.Console;
import cn.hutool.json.JSONUtil;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.enumeration.DatabaseTypeEnum;
import xyz.esion.manage.model.database.mysql.Database4MySql;
import xyz.esion.manage.model.database.mysql.Field4MySql;
import xyz.esion.manage.model.database.mysql.Table4MySql;
import xyz.esion.manage.model.database.mysql.View4MySql;

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

    private final static String QUERY_DATABASE_NAME = "select database() as name;";
    private final static String QUERY_TABLE = "show table status";
    private final static String QUERY_VIEW = "select * from information_schema.VIEWS";
    private final static String QUERY_FIELD = "SHOW FULL COLUMNS FROM ";

    public static String generateUrl(Database database, DatabaseTypeEnum databaseTypeEnum) {
        switch (databaseTypeEnum) {
            case MYSQL:
                // MySQL数据库
                return "jdbc:mysql://" + database.getHost() + ":" + database.getPort().toString() + "/" + database.getName() + "?" + database.getParameter();
            default:
                throw new IllegalArgumentException("不受支持的数据库类型：" + database.getType());
        }
    }

    /**
     * 获取数据源
     *
     * @param database 数据库信息
     * @return 数据源，如果连接失败返回null
     */
    public static DataSource getDataSource(Database database, DatabaseTypeEnum databaseTypeEnum) {
        HikariConfig config = new HikariConfig();
        config.setUsername(database.getUsername());
        config.setPassword(database.getPassword());
        config.setDriverClassName(databaseTypeEnum.getDriver());
        config.setJdbcUrl(generateUrl(database, databaseTypeEnum));
        return new HikariDataSource(config);
    }

    /**
     * 初始化MySQL数据库信息
     */
    public static Database4MySql initMySQLInformation(Connection connection) throws SQLException {
        Database4MySql database = new Database4MySql();
        // 查询数据库名称
        Statement dbStatement = connection.createStatement();
        ResultSet dbResult = dbStatement.executeQuery(QUERY_TABLE);
        dbResult.next();
        String databaseName = dbResult.getString(1);
        database.setName(databaseName);
        // 查询全部的表
        Statement tableStatement = connection.createStatement();
        ResultSet tablesResult = tableStatement.executeQuery(QUERY_TABLE);
        List<Table4MySql> tables = new ArrayList<>();
        while (tablesResult.next()) {
            String comment = tablesResult.getString(Table4MySql.COLUMNS[17]);
            if ("VIEW".equals(comment)) {
                continue;
            }
            Table4MySql table = new Table4MySql();
            table.setName(tablesResult.getString(Table4MySql.COLUMNS[0]));
            table.setEngine(tablesResult.getString(Table4MySql.COLUMNS[1]));
            table.setVersion(tablesResult.getInt(Table4MySql.COLUMNS[2]));
            table.setRowFormat(tablesResult.getString(Table4MySql.COLUMNS[3]));
            table.setRows(tablesResult.getInt(Table4MySql.COLUMNS[4]));
            table.setAvgRowLength(tablesResult.getInt(Table4MySql.COLUMNS[5]));
            table.setDataLength(tablesResult.getInt(Table4MySql.COLUMNS[6]));
            table.setMaxDataLength(tablesResult.getInt(Table4MySql.COLUMNS[7]));
            table.setIndexLength(tablesResult.getInt(Table4MySql.COLUMNS[8]));
            table.setDataFree(tablesResult.getInt(Table4MySql.COLUMNS[9]));
            table.setAutoIncrement(tablesResult.getInt(Table4MySql.COLUMNS[10]));
            table.setCreateTime(tablesResult.getDate(Table4MySql.COLUMNS[11]));
            table.setUpdateTime(tablesResult.getDate(Table4MySql.COLUMNS[12]));
            table.setCheckTime(tablesResult.getDate(Table4MySql.COLUMNS[13]));
            table.setCollation(tablesResult.getString(Table4MySql.COLUMNS[14]));
            table.setCheckSum(tablesResult.getString(Table4MySql.COLUMNS[15]));
            table.setCreateOptions(tablesResult.getString(Table4MySql.COLUMNS[16]));
            table.setComment(comment);
            // 查询字段
            Statement fieldStatement = connection.createStatement();
            ResultSet fieldResult = fieldStatement.executeQuery(QUERY_FIELD + table.getName());
            List<Field4MySql> fields = new ArrayList<>();
            while (fieldResult.next()) {
                Field4MySql field = new Field4MySql();
                field.setName(fieldResult.getString(Field4MySql.COLUMN[0]));
                field.setType(fieldResult.getString(Field4MySql.COLUMN[1]));
                field.setCollation(fieldResult.getString(Field4MySql.COLUMN[2]));
                field.setIsNull(fieldResult.getString(Field4MySql.COLUMN[3]));
                field.setKey(fieldResult.getString(Field4MySql.COLUMN[4]));
                field.setDefaultValue(fieldResult.getString(Field4MySql.COLUMN[5]));
                field.setExtra(fieldResult.getString(Field4MySql.COLUMN[6]));
                field.setPrivileges(fieldResult.getString(Field4MySql.COLUMN[7]));
                field.setComment(fieldResult.getString(Field4MySql.COLUMN[8]));
                fields.add(field);
            }
            table.setFields(fields);
            tables.add(table);
        }
        database.setTables(tables);
        // 查询全部的视图
        Statement viewStatement = connection.createStatement();
        ResultSet viewResult = viewStatement.executeQuery(QUERY_VIEW);
        List<View4MySql> views = new ArrayList<>();
        while (viewResult.next()) {
            String schema = viewResult.getString(View4MySql.COLUMN[0]);
            if (schema.equals(databaseName)) {
                View4MySql view = new View4MySql();
                view.setName(viewResult.getString(View4MySql.COLUMN[1]));
                view.setDefinition(viewResult.getString(View4MySql.COLUMN[2]));
                view.setCheckOption(viewResult.getString(View4MySql.COLUMN[3]));
                view.setIsUpdatable(viewResult.getString(View4MySql.COLUMN[4]));
                view.setSecurityType(viewResult.getString(View4MySql.COLUMN[5]));
                view.setCharacterSetClient(viewResult.getString(View4MySql.COLUMN[6]));
                view.setCollationConnection(viewResult.getString(View4MySql.COLUMN[7]));
                views.add(view);
            }
        }
        database.setViews(views);
        connection.close();
        return database;
    }

}

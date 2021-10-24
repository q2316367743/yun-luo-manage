package xyz.esion.manage.model.database;

import lombok.Data;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.enumeration.DatabaseStatusEnum;

import javax.sql.DataSource;
import java.util.List;

/**
 * 记录数据库连接时的基本信息<br />
 * 仅支持MySQL数据库
 *
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class DatabaseInfo {

    /**
     * 数据库名称
     */
    private String name;

    /**
     * 数据源
     * */
    private DataSource dataSource;

    /**
     * 数据库基本信息
     * */
    private Database database;

    /**
     * 表
     * */
    private List<Table> tables;

    private List<View> views;

    /**
     * 获取数据连接状态
     * */
    public DatabaseStatusEnum getStatus(){
        return DatabaseStatusEnum.CONNECTION;
    }

}

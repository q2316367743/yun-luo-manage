package xyz.esion.manage.model.database;

import lombok.Data;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.enumeration.DatabaseStatusEnum;

import javax.sql.DataSource;

/**
 * 记录数据库连接时的基本信息
 *
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class DatabaseBase<T> {

    /**
     * 数据源
     * */
    private DataSource dataSource;

    /**
     * 数据库基本信息
     * */
    private Database database;

    /**
     * 数据库的信息
     * */
    private T information;

    /**
     * 获取数据连接状态
     * */
    public DatabaseStatusEnum getStatus(){
        return DatabaseStatusEnum.CONNECTION;
    }

}

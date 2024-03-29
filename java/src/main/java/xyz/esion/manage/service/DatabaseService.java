package xyz.esion.manage.service;

import cn.hutool.core.lang.Dict;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.exception.DatabaseException;
import xyz.esion.manage.model.database.DatabaseInfo;
import xyz.esion.manage.option.DatabaseOption;
import xyz.esion.manage.view.database.DatabaseInfoView;
import xyz.esion.manage.view.database.DatabaseListView;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>本地数据库只记录数据库基本信息；</p>
 * <p>连接信息，数据库详细信息都记录在内存中</p>
 *
 *
 * @author Esion
 * @since 2021/7/16
 */
public interface DatabaseService {

    /**
     * 存放内存中数据库连接，状态，连接字符串
     * */
    Map<String, DatabaseInfo> DATABASE_STATUS_MAP = new ConcurrentHashMap<>();

    /**
     * 新增一个数据库信息，新增时自动连接
     *
     * @param database 数据库信息
     * @return 操作结果
     * */
    boolean save(DatabaseOption database);

    /**
     * 查询全部的数据库信息
     *
     * @return 全部的数据库信息
     * */
    List<DatabaseListView> list();

    /**
     * 更新一个数据库信息
     *
     * @param database 数据库信息
     * @return 操作结果
     * */
    boolean update(Database database);

    /**
     * 根据ID删除数据库信息
     *
     * @param id 数据库ID
     * @return 操作结果
     * */
    boolean remove(String id);

    /**
     * 刷新数据库信息
     *
     * @param id 数据库信息ID
     * @return 刷新结果
     * */
    DatabaseInfoView refresh(String id);

    /**
     * 获取一个数据库的状态
     *
     * @param id 数据库ID
     * @return 状态
     * */
    Boolean status(String id);

    /**
     * 执行一句查询SQL并返回结果
     *
     * @param id 数据库ID
     * @param sql SQL
     * @return 结果
     * */
    Dict executeQuery(String id, String sql);

    /**
     * 根据ID查询数据库全部信息
     *
     * @param id 数据库ID
     * @return 详细信息
     * */
    DatabaseInfoView info(String id) throws DatabaseException;

    /**
     * 获取本机数据库中存储的数据库数量
     *
     * @return 数量
     * */
    Integer count();

}

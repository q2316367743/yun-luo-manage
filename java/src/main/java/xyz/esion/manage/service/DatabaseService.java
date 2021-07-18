package xyz.esion.manage.service;

import cn.hutool.core.lang.Dict;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.view.DatabaseInfoView;
import xyz.esion.manage.view.DatabaseListView;

import java.util.List;

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
     * 新增一个数据库信息，新增时自动连接
     *
     * @param database 数据库信息
     * @return 操作结果
     * */
    boolean add(Database database);

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
     * 连接一个数据库
     *
     * @param id 数据库ID
     * @return 操作结果
     * */
    boolean connect(String id);

    /**
     * 获取一个数据库的状态
     *
     * @param id 数据库ID
     * @return 状态
     * */
    Integer status(String id);

    /**
     * 执行一句SQL并返回结果
     *
     * @param id 数据库ID
     * @param sql SQL
     * @return 结果
     * */
    Dict exec(String id, String sql);

    /**
     * 根据ID查询数据库全部信息
     *
     * @param id 数据库ID
     * @return 详细信息
     * */
    DatabaseInfoView info(String id);

}

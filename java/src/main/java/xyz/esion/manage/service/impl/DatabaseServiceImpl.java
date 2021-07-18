package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.enumeration.DatabaseTypeEnum;
import xyz.esion.manage.mapper.DatabaseMapper;
import xyz.esion.manage.model.database.mysql.Database4MySql;
import xyz.esion.manage.service.DatabaseService;
import xyz.esion.manage.model.database.DatabaseBase;
import xyz.esion.manage.util.DatabaseUtil;
import xyz.esion.manage.view.DatabaseInfoView;
import xyz.esion.manage.view.DatabaseListView;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Service
public class DatabaseServiceImpl implements DatabaseService {

    /**
     * 存放内存中数据库连接，状态，连接字符串
     * */
    private final static Map<String, DatabaseBase> DATABASE_STATUS_MAP = new ConcurrentHashMap<>();

    /**
     * 本身数据库存放的信息
     * */
    private DatabaseMapper databaseMapper;

    @Override
    public boolean add(Database database) {
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.getByType(database.getType());
        // 连接数据库
        DataSource dataSource = DatabaseUtil.getDataSource(database, databaseTypeEnum);
        try {
            Connection connection = dataSource.getConnection();
            switch (databaseTypeEnum){
                case MYSQL:
                    // 获取数据库连接
                    Database4MySql mySql = DatabaseUtil.initMySQLInformation(connection);
                    // 插入到数据库中
                    databaseMapper.insert(database);
                    // 将对象放到内存中
                    DatabaseBase<Database4MySql> databaseBase = new DatabaseBase<>();
                    databaseBase.setDatabase(database);
                    databaseBase.setDataSource(dataSource);
                    databaseBase.setInformation(mySql);
                    DATABASE_STATUS_MAP.put(database.getId(), databaseBase);
                    return true;
                default:
                    return false;
            }
        }catch (SQLException exception){
            // 获取数据库连接失败
            return false;
        }
    }

    @Override
    public List<DatabaseListView> list() {
        // 查询数据库
        List<Database> databases = databaseMapper.selectList(new QueryWrapper<>());
        List<DatabaseListView> result = new ArrayList<>();
        for (Database database : databases) {
            DatabaseListView databaseListView = BeanUtil.copyProperties(database, DatabaseListView.class);
            DatabaseBase databaseBase = DATABASE_STATUS_MAP.get(database.getId());
            if (databaseBase != null){
                databaseListView.setStatus(databaseBase.getStatus().getValue());
            }
            result.add(databaseListView);
        }
        return result;
    }

    @Override
    public boolean update(Database database) {
        return false;
    }

    @Override
    public boolean remove(String id) {
        // 删除内存中数据库连接信息
        DATABASE_STATUS_MAP.remove(id);
        // 数据库删除信息
        Database database = new Database();
        database.setId(id);
        database.setIsDelete(1);
        databaseMapper.updateById(database);
        return true;
    }

    @Override
    public DatabaseListView refresh(String id) {
        // 获取数据库信息
        // 如果内存中存在，则更新数据库信息
        // 如果刷新失败，返回内存中信息
        return null;
    }

    /**
     * 根据ID获取数据源
     *
     * @param id 数据ID
     * @return  数据源
     * */
    private DataSource connect(String id) {
        if (DATABASE_STATUS_MAP.containsKey(id)){
            return DATABASE_STATUS_MAP.get(id).getDataSource();
        }
        Database database = databaseMapper.selectById(id);
        if (database == null){
            throw new IllegalArgumentException("不存在数据库");
        }
        DatabaseTypeEnum databaseTypeEnum = DatabaseTypeEnum.getByType(database.getType());
        // 连接数据库
        DataSource dataSource = DatabaseUtil.getDataSource(database, databaseTypeEnum);
        try {
            Connection connection = dataSource.getConnection();
            switch (databaseTypeEnum){
                case MYSQL:
                    // 获取数据库连接
                    Database4MySql mySql = DatabaseUtil.initMySQLInformation(connection);
                    // 将对象放到内存中
                    DatabaseBase<Database4MySql> databaseBase = new DatabaseBase<>();
                    databaseBase.setDatabase(database);
                    databaseBase.setDataSource(dataSource);
                    databaseBase.setInformation(mySql);
                    DATABASE_STATUS_MAP.put(database.getId(), databaseBase);
                    return dataSource;
                default:
                    return null;
            }
        }catch (SQLException exception){
            // 获取数据库连接失败
            return null;
        }
    }

    /**
     * 根据数据库信息连接，数据库信息需要是本地数据库中得到
     *
     * @param database 数据库信息
     * @return 连接结果，数据源
     * */
    private DataSource connect(Database database){

    }

    @Override
    public Boolean status(String id) {
        return DATABASE_STATUS_MAP.containsKey(id);
    }

    @Override
    public Dict exec(String id, String sql) {
        Connection connection = null;
        try {
            if (DATABASE_STATUS_MAP.containsKey(id)){
                // 如果存在
                connection = DATABASE_STATUS_MAP.get(id).getDataSource().getConnection();

            }else {
                // 如果不存在
                Database database = databaseMapper.selectById(id);
                Assert.isNull(database, "ID错误");
                DataSource dataSource = connect(database);
                Assert.isNull(database, "数据库连接失败");
                connection = dataSource.getConnection();
            }
        }catch (SQLException ignore){
            return null;
        }finally {
            if (connection != null){
                try {
                    connection.close();
                } catch (SQLException ignored) {}
            }
        }
    }

    @Override
    public DatabaseInfoView info(String id) {
        return null;
    }

}

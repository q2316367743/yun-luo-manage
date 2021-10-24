package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.enumeration.DatabaseStatusEnum;
import xyz.esion.manage.exception.DatabaseException;
import xyz.esion.manage.mapper.DatabaseMapper;
import xyz.esion.manage.option.DatabaseOption;
import xyz.esion.manage.service.DatabaseService;
import xyz.esion.manage.model.database.DatabaseInfo;
import xyz.esion.manage.util.DatabaseUtil;
import xyz.esion.manage.view.DatabaseInfoView;
import xyz.esion.manage.view.DatabaseListView;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Service
@RequiredArgsConstructor
public class DatabaseServiceImpl implements DatabaseService {

    /**
     * 本身数据库存放的信息
     * */
    private final DatabaseMapper databaseMapper;

    @Override
    @Transactional(rollbackFor = DatabaseException.class)
    public boolean save(DatabaseOption option) {
        Database database = BeanUtil.copyProperties(option, Database.class);
        // 连接数据库
        DataSource dataSource = DatabaseUtil.getDataSource(database);
        try {
            Connection connection = dataSource.getConnection();
            // 获取数据库连接
            DatabaseInfo databaseInfo = DatabaseUtil.initMySqlInformation(connection);
            // 插入到数据库中
            databaseMapper.insert(database);
            databaseInfo.setDatabase(database);
            databaseInfo.setDataSource(dataSource);
            // 将对象放到内存中
            DATABASE_STATUS_MAP.put(database.getId(), databaseInfo);
            return true;
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
            DatabaseInfo databaseInfo = DATABASE_STATUS_MAP.get(database.getId());
            if (databaseInfo != null){
                databaseListView.setStatus(databaseInfo.getStatus().getValue());
            }else {
                databaseListView.setStatus(DatabaseStatusEnum.DIS_CONNECTION.getValue());
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
    public DatabaseInfoView refresh(String id) {
        // 获取数据库信息
        Database database = databaseMapper.selectById(id);
        if (database == null) {
            return null;
        }
        DataSource dataSource = DatabaseUtil.getDataSource(database);
        try(Connection connection = dataSource.getConnection();) {
            DatabaseInfo databaseInfo = DatabaseUtil.initMySqlInformation(connection);
            databaseInfo.setDataSource(dataSource);
            databaseInfo.setDatabase(database);
            // 如果内存中存在，则更新数据库信息
            if (DATABASE_STATUS_MAP.containsKey(id)) {
                DATABASE_STATUS_MAP.remove(id);
                DATABASE_STATUS_MAP.put(id, databaseInfo);
            }
            return DatabaseInfoView.parse(databaseInfo);
        }catch (Exception ignore) {
            if (DATABASE_STATUS_MAP.containsKey(id)) {
                return DatabaseInfoView.parse(DATABASE_STATUS_MAP.get(id));
            }
            return null;
        }
        // 如果刷新失败，返回内存中信息
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
        // 连接数据库
        DataSource dataSource = DatabaseUtil.getDataSource(database);
        try {
            Connection connection = dataSource.getConnection();
            // 获取数据库连接
            DatabaseInfo databaseInfo = DatabaseUtil.initMySqlInformation(connection);
            // 将对象放到内存中
            databaseInfo.setDatabase(database);
            databaseInfo.setDataSource(dataSource);
            DATABASE_STATUS_MAP.put(database.getId(), databaseInfo);
            return dataSource;
        }catch (SQLException exception){
            // 获取数据库连接失败
            return null;
        }
    }


    @Override
    public Boolean status(String id) {
        return DATABASE_STATUS_MAP.containsKey(id);
    }

    @Override
    public Dict executeQuery(String id, String sql) {
        Connection connection = null;
        try {
            if (DATABASE_STATUS_MAP.containsKey(id)){
                // 如果存在
                connection = DATABASE_STATUS_MAP.get(id).getDataSource().getConnection();

            }else {
                // 如果不存在
                DataSource dataSource = connect(id);
                if (dataSource == null) {
                    throw new DatabaseException("数据库连接失败");
                }
                connection = dataSource.getConnection();
            }
            Dict result = new Dict();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            ResultSetMetaData metaData = resultSet.getMetaData();
            int columnCount = metaData.getColumnCount();
            List<String> columnNames = new ArrayList<>();
            for (int i = 0; i < columnCount; i++) {
                String columnName = metaData.getColumnName(i + 1);
                columnNames.add(columnName);
            }
            // 表中字段
            result.set("column_names", columnNames);
            List<Dict> data = new ArrayList<>();
            while (resultSet.next()){
                Dict item = new Dict();
                for (String columnName : columnNames) {
                    item.set(columnName, resultSet.getObject(columnName));
                }
                data.add(item);
            }
            result.set("data", data);
            return result;
        }catch (SQLException | DatabaseException ignore){
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
    public DatabaseInfoView info(String id) throws DatabaseException {
        DatabaseInfo databaseInfo = DATABASE_STATUS_MAP.get(id);
        if (databaseInfo == null) {
            throw new DatabaseException("ID不存在");
        }
        return DatabaseInfoView.parse(databaseInfo);
    }

    @Override
    public Integer count() {
        return databaseMapper.selectCount(new QueryWrapper<>());
    }

}

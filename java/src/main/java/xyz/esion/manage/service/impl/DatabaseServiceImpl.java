package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.lang.Dict;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.mapper.DatabaseMapper;
import xyz.esion.manage.service.DatabaseService;
import xyz.esion.manage.model.database.DatabaseBase;
import xyz.esion.manage.view.DatabaseInfoView;
import xyz.esion.manage.view.DatabaseListView;

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
        return false;
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
                databaseListView.setStatus(databaseBase.getStatus());
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
        return false;
    }

    @Override
    public boolean connect(String id) {
        return false;
    }

    @Override
    public Integer status(String id) {
        return null;
    }

    @Override
    public Dict exec(String id, String sql) {
        return null;
    }

    @Override
    public DatabaseInfoView info(String id) {
        return null;
    }

}

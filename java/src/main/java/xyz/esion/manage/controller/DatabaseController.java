package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.entity.Database;
import xyz.esion.manage.exception.DatabaseException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.DatabaseOption;
import xyz.esion.manage.service.DatabaseService;

/**
 * 数据库管理
 *
 * @author Esion
 * @since 2021/7/16
 */
@RestController
@RequestMapping("api/database")
@SaCheckLogin
@RequiredArgsConstructor
public class DatabaseController {

    private final DatabaseService databaseService;

    /**
     * 新增一个数据库信息，新增时自动连接
     *
     * @param option 数据库信息
     * @return 操作结果
     * */
    @PostMapping("save")
    public Result add(@RequestBody DatabaseOption option){
        return Result.choose(databaseService.save(option));
    }

    /**
     * 查询全部的数据库信息
     *
     * @return 全部的数据库信息
     * */
    @GetMapping
    public Result list(){
        return Result.success().items(databaseService.list());
    }

    /**
     * 更新一个数据库信息
     *
     * @param id 数据库ID
     * @param database 数据库信息
     * @return 操作结果
     * */
    @PutMapping("update/{id}")
    public Result update(@PathVariable("id")String id, @RequestBody Database database){
        database.setId(id);
        return Result.choose(databaseService.update(database));
    }

    /**
     * 根据ID删除数据库信息
     *
     * @param id 数据库ID
     * @return 操作结果
     * */
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable("id") String id){
        return Result.choose(databaseService.remove(id));
    }

    /**
     * 连接一个数据库
     *
     * @param id 数据库ID
     * @return 操作结果
     * */
    @PostMapping("connect/{id}")
    public Result refresh(@PathVariable("id") String id){
        return Result.success().item(databaseService.refresh(id));
    }

    /**
     * 获取一个数据库的状态
     *
     * @param id 数据库ID
     * @return 状态
     * */
    @GetMapping("status/{id}")
    public Result status(@PathVariable("id") String id) {
        return Result.success().item(databaseService.status(id));
    }

    /**
     * 执行一句SQL并返回结果
     *
     * @param id 数据库ID
     * @param sql SQL
     * @return 结果
     * */
    @PostMapping("execute_query/{id}")
    public Result executeQuery(@PathVariable("id") String id, @RequestBody String sql){
        return Result.success().items(databaseService.executeQuery(id, sql));
    }

    /**
     * 根据ID查询数据库全部信息
     *
     * @param id 数据库ID
     * @return 详细信息
     * */
    @GetMapping("info/{id}")
    public Result info(@PathVariable("id") String id) throws DatabaseException {
        return Result.success().item(databaseService.info(id));
    }

}

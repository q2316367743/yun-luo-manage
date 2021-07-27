package xyz.esion.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.esion.manage.model.task.Task;
import xyz.esion.manage.view.TaskInfoView;
import xyz.esion.manage.view.TaskListView;

import java.util.List;

/**
 * @author Esion
 * @since 2021/7/27
 */
public interface TaskService {

    /**
     * 增加一个任务
     *
     * @param task 任务
     * @return 任务ID
     * */
    Long add(Task task);

    /**
     * 查询当前进行中的任务
     *
     * @return 进行中的任务
     */
    List<TaskListView> listByNow();

    /**
     * 分页查询全部的任务列表
     *
     * @param page 页码
     * @param limit 每页数目
     * @return
     */
    Page<TaskListView> page(Integer page, Integer limit);

    /**
     * 取消一个任务
     *
     * @param id 取消任务
     * @return 结果
     */
    boolean cancelTask(Long id);

    /**
     * 根据id获取任务信息
     *
     * @param id 任务ID
     * @return
     */
    TaskInfoView getById(Long id);

}

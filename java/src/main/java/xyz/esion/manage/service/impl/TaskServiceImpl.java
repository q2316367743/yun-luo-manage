package xyz.esion.manage.service.impl;

import cn.hutool.core.thread.ThreadUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Service;
import xyz.esion.manage.model.task.Task;
import xyz.esion.manage.service.TaskService;
import xyz.esion.manage.view.task.TaskInfoView;
import xyz.esion.manage.view.task.TaskListView;

import java.util.List;
import java.util.concurrent.ExecutorService;

/**
 * @author Esion
 * @since 2021/7/27
 */
@Service
public class TaskServiceImpl implements TaskService {

    private final ExecutorService executorService;

    public TaskServiceImpl() {
        // 初始化线程池
        this.executorService = ThreadUtil.newExecutor(4, 10);
    }

    @Override
    public Long add(Task<?> task) {
    	executorService.submit(task);
        return null;
    }

    @Override
    public List<TaskListView> listByNow() {
        return null;
    }

    @Override
    public Page<TaskListView> page(Integer page, Integer limit) {
        return null;
    }

    @Override
    public boolean cancelTask(Long id) {
        return false;
    }

    @Override
    public TaskInfoView getById(Long id) {
        return null;
    }
    
}

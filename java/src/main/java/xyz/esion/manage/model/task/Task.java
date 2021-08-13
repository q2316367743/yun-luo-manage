package xyz.esion.manage.model.task;

import java.util.concurrent.Callable;

import xyz.esion.manage.exception.TaskException;

/**
 * @author Esion
 * @since 2021/7/27
 */
public interface Task<T> extends Callable<T> {

    /**
     * 执行任务
     *
     * @param step 执行到第几步
     * @param operation 任务执行信息
     * @throws TaskException 任务执行异常
     */
    void run(Integer step, TaskOperation operation) throws TaskException;

}

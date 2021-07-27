package xyz.esion.manage.model.task;

/**
 * 任务执行详情
 * @author Esion
 * @since 2021/7/27
 */
public interface TaskOperation {

    /**
     * 根据ID查询任务是否取消
     *
     * @param id 任务ID
     * @return 结果
     */
    boolean isCancel(Long id);

    /**
     * 写日志，每一次写日志增加一步
     *
     * @param id 任务ID
     * @param content 日志内容
     */
    void writeLog(Long id, String content);

    /**
     * 手动设置进度
     *
     * @param id 任务ID
     * @param progress 进度
     */
    void setProgress(Long id, Double progress);

}

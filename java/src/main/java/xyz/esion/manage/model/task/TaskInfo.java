package xyz.esion.manage.model.task;

import lombok.Data;
import xyz.esion.manage.enumeration.TaskStatusEnum;

/**
 * 记录任务信息
 * @author Esion
 * @since 2021/7/27
 */
@Data
public class TaskInfo {

    private Long id;

    /**
     * 任务名称
     */
    private String name;

    /**
     * 任务下载
     */
    private String description;

    /**
     * 任务状态
     */
    private TaskStatusEnum status;

    /**
     * 日志
     */
    private StringBuilder log;

    /**
     * 当前打印日志
     * */
    private String currentLog;

    /**
     * 是否取消
     * */
    private Boolean isCancel;

    /**
     * 任务进度，默认：currentStep / allStep
     */
    private Double progress;

    /**
     * 一共执行
     */
    private Integer allStep;

    /**
     * 当前执行到
     * */
    private Integer currentStep;

}

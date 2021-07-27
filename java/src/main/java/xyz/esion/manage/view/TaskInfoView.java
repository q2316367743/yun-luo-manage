package xyz.esion.manage.view;

import lombok.Data;
import xyz.esion.manage.enumeration.TaskStatusEnum;

import java.io.Serializable;

/**
 * 任务详情
 *
 * @author Esion
 * @since 2021/7/27
 */
@Data
public class TaskInfoView implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 任务进度
     */
    private Double progress;

    /**
     * 任务状态
     */
    private TaskStatusEnum status;

    /**
     * 日志
     */
    private String log;

}

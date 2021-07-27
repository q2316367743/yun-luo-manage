package xyz.esion.manage.view;

import lombok.Data;
import xyz.esion.manage.enumeration.TaskStatusEnum;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/7/27
 */
@Data
public class TaskListView implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private Double progress;

    private TaskStatusEnum status;

}

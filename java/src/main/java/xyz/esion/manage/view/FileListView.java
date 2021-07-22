package xyz.esion.manage.view;

import lombok.Data;
import xyz.esion.manage.enumeration.FileTypeEnum;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>文件列表详情，基于 <em>ls -l</em> 命令</p>
 *
 * <p>参考：<a href="https://blog.csdn.net/LEON1741/article/details/82386520">linux命令 ll信息详解</a></p>
 * @author Esion
 * @since 2021/7/20
 */
@Data
public class FileListView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     * */
    private String name;

    /**
     * 文件类型
     * */
    private FileTypeEnum type;

    /**
     * 文件大小
     * */
    private Double size;

    /**
     * 文件单位
     * */
    private String unit;

    /**
     * 文件路径
     * */
    private String path;

    /**
     * 权限
     * */
    private String permission;

    /**
     * 拥有者
     * */
    private String owner;

    /**
     * 组
     * */
    private String group;

    /**
     * 更新时间
     * */
    private LocalDateTime updateTime;

}

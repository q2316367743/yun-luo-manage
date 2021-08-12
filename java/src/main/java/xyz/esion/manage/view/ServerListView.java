package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerListView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 服务器名称
     */
    private String name;

    /**
     * 服务器类型
     */
    private String type;

    /**
     * 是否启动
     */
    private Boolean status;

    /**
     * 服务器版本
     */
    private String version;

    /**
     * 创建时间
     */
    private Date createTime;

}

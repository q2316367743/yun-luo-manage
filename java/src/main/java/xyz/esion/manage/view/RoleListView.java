package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class RoleListView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 角色标识
     */
    private String value;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

}

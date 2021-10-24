package xyz.esion.manage.view.user;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class RoleInfoView implements Serializable {

    private static final long serialVersionUID = 1L;

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

    /**
     * 权限ID列表
     */
    private List<String> permissions;

}

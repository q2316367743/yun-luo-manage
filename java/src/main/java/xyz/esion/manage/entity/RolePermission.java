package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_role_permission
 * @author Esion
 * @since 2021/8/6
 */
@TableName(value ="t_role_permission")
@Data
public class RolePermission implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String roleId;

    /**
     * 
     */
    private String permissionId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
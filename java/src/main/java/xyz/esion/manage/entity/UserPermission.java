package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName v_user_permission
 * @author esion
 * @since 2021/8/11
 */
@TableName(value ="v_user_permission")
@Data
public class UserPermission implements Serializable {
    /**
     * 
     */
    private String userId;

    /**
     * 
     */
    private String permissionValue;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
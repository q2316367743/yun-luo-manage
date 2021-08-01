package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_server_config
 * @author Esion
 * @since 2021/7/31
 */
@TableName(value ="t_server_config")
@Data
public class ServerConfig implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String path;

    /**
     * 
     */
    private String serverId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
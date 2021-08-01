package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_server_command
 * @author Esion
 * @since 2021/7/31
 */
@TableName(value ="t_server_command")
@Data
public class ServerCommand implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String command;

    /**
     * 
     */
    private String serverId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
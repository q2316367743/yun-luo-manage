package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName t_database
 */
@TableName(value ="t_database")
@Data
public class Database implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.ASSIGN_ID)
    private String id;

    /**
     * 
     */
    private String nickname;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String host;

    /**
     * 
     */
    private Integer port;

    /**
     * 
     */
    private String parameter;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private Integer status;

    /**
     * 
     */
    private Integer type;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;

    /**
     * 
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
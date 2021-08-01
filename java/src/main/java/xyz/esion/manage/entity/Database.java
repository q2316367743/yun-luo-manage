package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;

/**
 * 
 * @TableName t_database
 * @author Esion
 * @since 2021/7/17
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
     * 其他参数
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
     * 数据库类型
     *
     * @see xyz.esion.manage.enumeration.DatabaseTypeEnum
     */
    private Integer type;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
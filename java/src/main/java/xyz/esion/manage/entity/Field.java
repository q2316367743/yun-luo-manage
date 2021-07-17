package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Data;

/**
 * 
 * @TableName t_field
 */
@TableName(value ="t_field")
@Data
public class Field implements Serializable {
    /**
     * 
     */
    @TableId
    private String id;

    /**
     * 
     */
    private String name;

    /**
     * 
     */
    private String type;

    /**
     * 
     */
    private String isNull;

    /**
     * 
     */
    private String keyType;

    /**
     * 
     */
    private String defaultValue;

    /**
     * 
     */
    private String extra;

    /**
     * 
     */
    private String databaseId;

    /**
     * 
     */
    private String tableId;

    /**
     * 
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 
     */
    private Integer isDelete;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
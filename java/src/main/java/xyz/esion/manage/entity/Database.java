package xyz.esion.manage.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import lombok.Data;

/**
 * 
 * @TableName t_database
 * @author Esion
 * @since 2021/7/16
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
     * 命名
     */
    private String nickname;

    /**
     * 名称
     */
    private String name;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * 参数
     */
    private String parameter;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * <p>状态：</p>
     *
     * <p>0：断开</p>
     * <p>1：连接中</p>
     * <p>2：连接成功</p>
     */
    private Integer status;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}
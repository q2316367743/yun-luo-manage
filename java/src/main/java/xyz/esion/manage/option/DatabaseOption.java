package xyz.esion.manage.option;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/10/24
 */
@Data
public class DatabaseOption implements Serializable {

    private static final long serialVersionUID = 1L;

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

}

package xyz.esion.manage.option;

import lombok.Data;

import java.io.Serializable;

/**
 * 手动设置服务器
 *
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;

    private Integer type;

    private String typeName;

    private String version;

}

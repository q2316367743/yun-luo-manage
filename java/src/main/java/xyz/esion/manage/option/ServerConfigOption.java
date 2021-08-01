package xyz.esion.manage.option;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务器配置
 *
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerConfigOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String path;

}

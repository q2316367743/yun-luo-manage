package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerConfigView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 配置文件所在路径
     */
    private String path;

}

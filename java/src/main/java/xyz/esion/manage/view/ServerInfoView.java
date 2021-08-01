package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 服务器详细信息
 *
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerInfoView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 服务器名称
     */
    private String name;

    /**
     * 服务器类型，枚举类型具有一些特殊的功能
     */
    private Integer type;

    /**
     * 服务器类型名称
     */
    private String typeName;

    /**
     * 服务器版本
     */
    private String version;

    /**
     * 服务器命令列表
     */
    List<ServerCommandView> commands;

    /**
     * 配置文件路径
     */
    List<ServerConfigView> configs;

}

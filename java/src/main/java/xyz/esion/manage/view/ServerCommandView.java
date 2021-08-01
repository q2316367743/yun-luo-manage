package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;

/**
 * 服务器命令列表
 *
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerCommandView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 命令名称
     */
    private String name;

    /**
     * 命令
     */
    private String command;

}

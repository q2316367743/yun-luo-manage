package xyz.esion.manage.option;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/7/31
 */
@Data
public class ServerCommandOption implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 命令别名
     */
    private String name;

    /**
     *
     */
    private String command;

    /**
     *
     */
    private String serverId;

}

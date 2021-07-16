package xyz.esion.manage.view;

import java.util.List;

/**
 * @author Esion
 * @since 2021/7/16
 */
public class DatabaseView {

    private String id;

    /**
     * 命名
     */
    private String nickname;

    /**
     * 主机
     */
    private String host;

    /**
     * 端口
     */
    private Integer port;

    /**
     * <p>状态：</p>
     *
     * <p>0：断开</p>
     * <p>1：连接中</p>
     * <p>2：连接成功</p>
     */
    private Integer status;

    /**
     * 表
     * */
    List<TableView> tables;

}

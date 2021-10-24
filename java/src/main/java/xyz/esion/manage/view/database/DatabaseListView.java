package xyz.esion.manage.view.database;

import lombok.Data;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class DatabaseListView {

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

}

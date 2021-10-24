package xyz.esion.manage.view.database;

import lombok.Data;
import xyz.esion.manage.model.database.DatabaseInfo;

import java.util.List;

/**
 * @author Esion
 * @since 2021/7/16
 */
@Data
public class DatabaseInfoView {

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

    List<ViewView> views;

    public static DatabaseInfoView parse(DatabaseInfo databaseInfo) {
        DatabaseInfoView view = new DatabaseInfoView();
        return view;
    }

}

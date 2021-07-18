package xyz.esion.manage.model.database.mysql;

import lombok.Data;

/**
 * MySQL的视图
 *
 * @author Esion
 * @since 2021/7/18
 */
@Data
public class View4MySql {

    public static final String[] COLUMN = new String[]{"TABLE_SCHEMA", "TABLE_NAME", "VIEW_DEFINITION", "CHECK_OPTION",
            "IS_UPDATABLE", "SECURITY_TYPE", "CHARACTER_SET_CLIENT", "COLLATION_CONNECTION"
    };

    private String name;

    private String definition;

    private String checkOption;

    private String isUpdatable;

    private String securityType;

    private String characterSetClient;

    private String collationConnection;

}

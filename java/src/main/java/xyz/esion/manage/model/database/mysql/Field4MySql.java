package xyz.esion.manage.model.database.mysql;

import lombok.Data;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Data
public class Field4MySql{

    public static final String[] COLUMN = new String[]{"Field", "Type", "Collation", "Null", "Key", "Default",
            "Extra", "Privileges", "Comment"};

    /**
     * 字段名
     * */
    private String name;

    private String type;

    private String collation;

    private String isNull;

    private String key;

    private String defaultValue;

    private String extra;

    private String privileges;

    private String comment;

}

package xyz.esion.manage.enumeration;

/**
 * 支持的数据库类型
 *
 * @author Esion
 * @since 2021/7/17
 */
public enum DatabaseTypeEnum {

    // MySQL
    MYSQL(1);

    private Integer type;

    DatabaseTypeEnum(Integer type) {
        this.type = type;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}

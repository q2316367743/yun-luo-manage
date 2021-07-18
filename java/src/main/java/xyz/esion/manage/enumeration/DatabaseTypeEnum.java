package xyz.esion.manage.enumeration;

/**
 * 支持的数据库类型
 *
 * @author Esion
 * @since 2021/7/17
 */
public enum DatabaseTypeEnum {

    // MySQL
    MYSQL(1, "com.mysql.cj.jdbc.Driver");

    private Integer type;

    private String driver;

    DatabaseTypeEnum(Integer type, String driver) {
        this.type = type;
        this.driver = driver;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }
}

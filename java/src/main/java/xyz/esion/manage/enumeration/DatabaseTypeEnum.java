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

    public static DatabaseTypeEnum getByType(Integer type){
        for (DatabaseTypeEnum value : values()) {
            if (value.getType().equals(type)){
                return value;
            }
        }
        throw new IllegalArgumentException("不受支持的数据库类型：" + type);
    }

}

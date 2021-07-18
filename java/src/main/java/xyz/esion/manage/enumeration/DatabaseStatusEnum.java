package xyz.esion.manage.enumeration;

/**
 * 数据库状态
 *
 * @author Esion
 * @since 2021/7/18
 */
public enum DatabaseStatusEnum {

    // 连接中
    CONNECTION(1),
    // 断开连接
    DIS_CONNECTION(2);

    private Integer value;

    DatabaseStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}

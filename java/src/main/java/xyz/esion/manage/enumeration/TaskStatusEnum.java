package xyz.esion.manage.enumeration;

/**
 * @author Esion
 * @since 2021/7/27
 */
public enum TaskStatusEnum {

    // 准备中
    READY(1),
    // 进行中
    ING(2),
    // 成功
    SUCCESS(3),
    // 失败
    FAIL(4),
    // 取消
    CANCEL(5)
    ;

    private Integer value;

    TaskStatusEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

}

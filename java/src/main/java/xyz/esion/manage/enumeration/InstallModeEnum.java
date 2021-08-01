package xyz.esion.manage.enumeration;

/**
 * 安装方式
 *
 * @author Esion
 * @since 2021/7/31
 */
public enum InstallModeEnum {

    // 通过包管理器安装
    PACKAGE(1),
    // 通过源码安装
    SOURCE(2);

    private Integer value;

    InstallModeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

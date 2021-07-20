package xyz.esion.manage.enumeration;

/**
 * @author Esion
 * @since 2021/7/20
 */
public enum FileTypeEnum {

    // 文件夹
    FOLDER(1);

    private Integer value;

    FileTypeEnum(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}

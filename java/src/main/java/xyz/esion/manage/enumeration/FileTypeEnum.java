package xyz.esion.manage.enumeration;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Esion
 * @since 2021/7/20
 */
@Slf4j
public enum FileTypeEnum {

    // 文件夹
    FOLDER(1, 'd'),
    // 文件，不支持下载
    FILE(2, '-'),
    // 链接文件
    LINK(3, 'l'),
    // 管理文件
    MANAGE(4, 'p'),
    // 块设备文件
    BLOCK_QUIPMENT(4, 'b'),
    // 字符设备文件
    CHAR_EQUIPMENT(6, 'c');
    /**
     * 枚举值
     * */
    Integer value;

    Character type;

    FileTypeEnum(Integer value, Character type) {
        this.value = value;
        this.type = type;
    }

    public static FileTypeEnum parse(Character type){
        for (FileTypeEnum item : values()) {
            if (item.type.equals(type)){
                return item;
            }
        }
        log.error("不受支持的文件类型");
        return FILE;
    }

}

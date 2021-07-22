package xyz.esion.manage.enumeration;

import cn.hutool.core.util.StrUtil;

/**
 * @author Esion
 * @since 2021/7/20
 */
public enum FileTypeEnum {

    // 文件夹
    FOLDER(1, new String[]{}),
    // 文件，不支持下载
    FILE(2, new String[]{}),
    // 图片
    IMAGE(3, new String[]{"jpg", "jpeg", "png"}),
    // 视频
    VIDEO(4, new String[]{"mp4", "webm"}),
    // 代码
    CODE(5, new String[]{"java", "c", "cpp", "html"});

    /**
     * 枚举值
     * */
    Integer value;

    /**
     * 包含文件
     * */
    String[] include;

    FileTypeEnum(Integer value, String[] include) {
        this.value = value;
        this.include = include;
    }

    /**
     * 根据关键字获取文件类型
     * */
    public static Integer getTypeByKeyword(String keyword){
        if (StrUtil.containsAny(keyword, IMAGE.include)){
            return IMAGE.value;
        }else if (StrUtil.containsAny(keyword, VIDEO.include)){
            return VIDEO.value;
        }else if (StrUtil.containsAny(keyword, CODE.include)){
            return CODE.value;
        }
        return FileTypeEnum.FILE.value;
    }

}

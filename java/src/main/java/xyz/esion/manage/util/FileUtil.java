package xyz.esion.manage.util;

import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;

/**
 * @author Esion
 * @since 2021/8/11
 */
public class FileUtil {

    private final static Long UNIT = 1024L;
    private final static Double UNIT_DOUBLE = 1024.0;

    public static String beautify(String size){
        return beautify(Long.parseLong(size));
    }

    public static String beautify(Long size){
        if (size > UNIT * UNIT * UNIT){
            return NumberUtil.decimalFormat("#.00", size / UNIT_DOUBLE / UNIT / UNIT) + "GB";
        }else if (size > UNIT * UNIT){
            return NumberUtil.decimalFormat("#.00", size / UNIT_DOUBLE / UNIT) + "MB";
        }else if (size > UNIT){
            return NumberUtil.decimalFormat("#.00", size / UNIT_DOUBLE) + "KB";
        }else {
            return size + "B";
        }
    }

}

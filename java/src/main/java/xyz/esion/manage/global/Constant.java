package xyz.esion.manage.global;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Esion
 * @since 2021/7/15
 */
public class Constant {

    public static final List<String> FILE_CODE;

    static {
        // 初始化全局变量
        FILE_CODE = new CopyOnWriteArrayList<>(new String[]{"java", "c", "cpp", "py"});
    }

}

package xyz.esion.manage.global;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author Esion
 * @since 2021/7/15
 */
public class Constant {

    public static final Map<String, List<String>> FILE_TYPE;

    /**
     * Nginx基础下载连接
     */
    private static final String NGINX_BASE_URL = "http://nginx.org/download/nginx-";

    static {
        // 初始化全局变量

        // 文件类型
        FILE_TYPE = new ConcurrentHashMap<>();
        // 文本打开
        CopyOnWriteArrayList<String> code = new CopyOnWriteArrayList<>(
                new String[]{"java", "py", "txt", "conf", "vue", "html", "css", "js", "md", "sql"});
        FILE_TYPE.put("code", code);
    }

}

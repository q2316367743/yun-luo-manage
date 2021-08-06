package xyz.esion.manage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import xyz.esion.manage.view.FileListView;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 文件测试
 *
 * @author Esion
 * @since 2021/7/24
 */
public class FileTest {

    public static void main(String[] args) {
        Util();
    }

    public static void jdk(){
        File[] files = FileUtil.ls("/opt");
        for (File file : files) {
            Console.log("文件名：{}，路径：{}，文件大小：{}"
                    , file.getName(), file.getAbsolutePath(), file.getAbsoluteFile().getUsableSpace());
        }
    }

    public static void linux(){
        List<String> files = RuntimeUtil.execForLines("ls -l --full-time /opt/node-v14.17.3-linux-x64");
        for (String file : files) {
            FileListView list = FileListView.parse("/opt/node-v14.17.3-linux-x64", file);
            Console.log(list);
        }
    }

    public static void Util(){
        List<String> list = Arrays.asList("1", "2", "3");
        String join = CollUtil.join(list, "/");
        System.out.println(join);
    }

}

package xyz.esion.manage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.json.JSONUtil;
import xyz.esion.manage.view.file.FileInfoView;
import xyz.esion.manage.view.file.FileListView;

import java.io.File;
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
        state();
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

    public static void state(){
//        Console.log(RuntimeUtil.execForStr("stat -c \"|%n|%s|%G|%U|%a|%A|%W|%X|%Y|%Z|\" /home"));
        Console.log(JSONUtil
                .parseObj(FileInfoView.parse(RuntimeUtil.execForStr("stat -c \"|%n|%s|%G|%U|%a|%A|%W|%X|%Y|%Z|\" /home")))
                .toJSONString(4));
    }

    public static void ps(){
        String[] command = {"/bin/sh","-c","ps -aux | grep python"};
        List<String> lines = RuntimeUtil.execForLines(command);
        for (String line : lines) {
            if (!line.contains("grep python")){
                Console.log(line);
            }
        }
    }

    public static void tempPath(){
        Console.log(System.getProperty("java.io.tmpdir"));
    }

}

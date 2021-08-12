package xyz.esion.manage;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Console;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.json.JSONUtil;
import xyz.esion.manage.view.FileInfoView;
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
        ps();
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
        List<String> list = Arrays.asList(
                "  File: banner_linux.png",
                "  Size: 26631           Blocks: 56         IO Block: 4096   regular file",
                "Device: 2h/2d   Inode: 30680772462057261  Links: 1",
                "Access: (0644/-rw-r--r--)  Uid: (    0/    root)   Gid: (    0/    root)",
                "Access: 2021-08-10 18:33:52.636817400 +0800",
                "Modify: 2021-07-30 14:09:33.544315100 +0800",
                "Change: 2021-07-30 14:09:33.544315100 +0800",
                " Birth: -");
        Console.log(JSONUtil.parseObj(FileInfoView.parse(list)).toJSONString(4));
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

}

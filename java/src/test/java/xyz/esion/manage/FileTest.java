package xyz.esion.manage;

import cn.hutool.system.SystemUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

/**
 * 文件测试
 *
 * @author Esion
 * @since 2021/5/18
 */
public class FileTest {

    /**
     * 打印文件根目录
     * */
    @Test
    public void rootDirection(){
        File[] files = File.listRoots();
        System.out.println(Arrays.toString(files));
    }

    @Test
    public void getAllFile(){
        String path = "C:\\Users\\Esion";
        File file = new File(path);
        if(!file.exists() || file.isFile()){
            System.out.println("目录不存在或路径为文件");
            return;
        }
        File[] files = file.listFiles();
        System.out.println("文件名\t\t文件绝对路径\t是否为文件");
        for (File f : files) {
            System.out.println(f.getName() + "\t\t" + f.getAbsolutePath() + "\t" + f.isFile());
        }
    }

    @Test
    public void userPath(){
        System.out.println(SystemUtil.get("user.home"));
    }

    @Test
    public void charS(){
        System.out.println(File.separatorChar);
    }

}

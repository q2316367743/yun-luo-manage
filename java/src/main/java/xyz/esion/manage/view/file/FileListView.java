package xyz.esion.manage.view.file;

import cn.hutool.core.date.DateUtil;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import xyz.esion.manage.enumeration.FileTypeEnum;
import xyz.esion.manage.util.FileUtil;

import java.io.File;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>文件列表详情，基于 <em>ls -l</em> 命令</p>
 *
 * <p>参考：<a href="https://blog.csdn.net/LEON1741/article/details/82386520">linux命令 ll信息详解</a></p>
 * @author Esion
 * @since 2021/7/20
 */
@Data
public class FileListView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     * */
    private String name;

    /**
     * 文件类型
     * */
    private FileTypeEnum type;

    /**
     * 文件大小
     * */
    private String size;

    /**
     * 绝对大小
     * */
    private Long absSize;

    /**
     * 文件路径】
     * */
    private String path;

    /**
     * 权限
     * */
    private String permission;

    /**
     * 拥有者
     * */
    private String owner;

    /**
     * 组
     * */
    private String group;

    /**
     * 更新时间
     * */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime updateTime;

    /**
     * 解析一行<em>ls -l --full-time</em>命令
     *
     * @param path 目录
     * @param line 一行数据
     * @return 文件列表视图
     * */
    public static FileListView parse(String path, String line){
        FileListView view = new FileListView();
        String[] item = line.split("[ ]+");
        if (item.length != 9){
            return null;
        }
        view.setName(item[8]);
        view.setType(FileTypeEnum.parse(item[0].charAt(0)));
        long absSize = Long.parseLong(item[4]);
        view.setSize(FileUtil.beautify(absSize));
        view.setAbsSize(absSize);
        view.setPath(path + File.separator + item[8]);
        view.setPermission(item[0].substring(1));
        view.setOwner(item[2]);
        view.setGroup(item[3]);
        view.setUpdateTime(DateUtil.parseLocalDateTime(item[5] + " " + item[6].substring(0, 8)));
        return view;
    }

}

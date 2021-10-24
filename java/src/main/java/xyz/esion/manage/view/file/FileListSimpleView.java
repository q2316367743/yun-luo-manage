package xyz.esion.manage.view.file;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 简易文件视图
 *
 * @author Esion
 * @since 2021/7/30
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FileListSimpleView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    private String name;

    /**
     * 文件路径
     */
    private String path;

    /**
     * 是否是目录
     */
    private Boolean isDirectory;

}

package xyz.esion.manage.service;

import org.springframework.http.ResponseEntity;
import xyz.esion.manage.view.FileListView;

import java.util.List;

/**
 * 文件服务
 *
 * @author Esion
 * @since 2021/7/20
 */
public interface FileService {

    /**
     * 展示文件目录获取目录下的文件及文件夹
     *
     * @param path 目录
     * @return 目录下的文件及文件夹
     * */
    List<FileListView> show(String path);

    /**
     * 打开一个文件。
     *
     * @param path 目录
     * @param entity  返回对象
     * */
    void open(String path, ResponseEntity<Byte> entity);

}

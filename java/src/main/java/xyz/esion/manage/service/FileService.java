package xyz.esion.manage.service;

import xyz.esion.manage.view.FileListView;

import java.util.List;

/**
 * 文件服务
 *
 * @author Esion
 * @since 2021/7/20
 */
public interface FileService {

    /***/
    List<FileListView> open(String path);

}

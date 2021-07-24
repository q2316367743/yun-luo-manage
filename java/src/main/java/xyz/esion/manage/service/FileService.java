package xyz.esion.manage.service;

import xyz.esion.manage.exception.FileException;
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
    List<FileListView> ls(String path) throws FileException;

    /**
     * 打开一个文本文件，返回文件的字符串
     *
     * @param path 文件
     * @param charset 字符集，默认UTF-8
     * */
    String open(String path, String charset) throws FileException;

    /**
     * 打开一个文件，返回文件的字节数组。
     *
     * @param path 目录
     * */
    byte[] show(String path) throws FileException;

    /**
     * 移动一个/多个文件到新目录
     *
     * @param paths 文件/文件夹绝对路径
     * @param target 目标文件夹
     * @param isForce 是否强制覆盖
     * */
    boolean mv(List<String> paths, String target, boolean isForce) throws FileException;

    /**
     * 复制一个/多个文件到新目录
     *
     * 强制，递归，将权限复制
     *
     * @param paths 文件/文件夹绝对路径
     * @param target 目标文件夹
     * */
    boolean cp(List<String> paths, String target) throws FileException;

    /**
     * 重命名文件/文件夹
     *
     * @param path 源文件/文件夹所在目录
     * @param name 原先的名称
     * @param target 修改后的名称
     * */
    boolean rename(String path, String name, String target) throws FileException;

    /**
     * 删除一个文件/夹
     *
     * @param paths 文件/夹绝对路径
     * @param isForce 是否强制
     */
    boolean rm(List<String> paths, boolean isForce) throws FileException;

    /**
     * 新建文件
     *
     * @param path 文件/夹绝对路径
     * @param name 文件名
     */
    boolean touch(String path, String name) throws FileException;

    /**
     * 新建文件夹
     *
     * @param path 文件/夹绝对路径
     * @param name 文件夹名
     */
    boolean mkdir(String path, String name) throws FileException;

    /**
     * 写入一个文件
     *
     * @param path 文件绝对路径
     * @param content 文件内容
     */
    boolean write(String path, String content) throws FileException;

}

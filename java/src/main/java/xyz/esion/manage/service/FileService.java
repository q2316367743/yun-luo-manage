package xyz.esion.manage.service;

import org.springframework.web.multipart.MultipartFile;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.view.FileListSimpleView;
import xyz.esion.manage.view.FileListView;

import java.io.IOException;
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
     * @throws FileException 文件操作异常
     * */
    List<FileListView> ls(String path) throws FileException;

    /**
     * 打开一个文本文件，返回文件的字符串
     *
     * @param path 文件
     * @param charset 字符集，默认UTF-8
     * @return 结果
     * @throws FileException 文件操作异常
     * */
    String open(String path, String charset) throws FileException;

    /**
     * 打开一个文件，返回文件的字节数组。
     *
     * @param path 目录
     * @return 结果
     * @throws FileException 文件操作异常
     * */
    byte[] show(String path) throws FileException;

    /**
     * 移动一个/多个文件到新目录
     *
     * @param paths 文件/文件夹绝对路径
     * @param target 目标文件夹
     * @param isForce 是否强制覆盖
     * @return 结果
     * @throws FileException 文件操作异常
     * */
    boolean mv(List<String> paths, String target, boolean isForce) throws FileException;

    /**
     * 复制一个/多个文件到新目录
     *
     * 强制，递归，将权限复制
     *
     * @param paths 文件/文件夹绝对路径
     * @param target 目标文件夹
     * @return 结果
     * @throws FileException 文件操作异常
     * */
    boolean cp(List<String> paths, String target) throws FileException;

    /**
     * 重命名文件/文件夹
     *
     * @param path 源文件/文件夹所在目录
     * @param name 原先的名称
     * @param target 修改后的名称
     * @return 结果
     * @throws FileException 文件操作异常
     * */
    boolean rename(String path, String name, String target) throws FileException;

    /**
     * 删除一个文件/夹
     *
     * @param paths 文件/夹绝对路径
     * @param isForce 是否强制
     * @return 结果
     * @throws FileException 文件操作异常
     */
    boolean rm(List<String> paths, boolean isForce) throws FileException;

    /**
     * 新建文件
     *
     * @param path 文件/夹绝对路径
     * @param name 文件名
     * @return 结果
     * @throws FileException 文件操作异常
     */
    boolean touch(String path, String name) throws FileException;

    /**
     * 新建文件夹
     *
     * @param path 文件/夹绝对路径
     * @param name 文件夹名
     * @return 结果
     * @throws FileException 文件操作异常
     */
    boolean mkdir(String path, String name) throws FileException;

    /**
     * 写入一个文件
     *
     * @param path 文件绝对路径
     * @param charset 编码，默认UTF-8
     * @param content 文件内容
     * @return 结果
     * @throws FileException 文件操作异常
     */
    boolean write(String path, String charset, String content) throws FileException;

    /**
     * 文件上传
     *
     * @param path 上传文件的路径
     * @param file 文件
     * @return 结果
     * @throws FileException 文件操作异常
     * @throws IOException IO异常
     */
    boolean upload(String path, MultipartFile file) throws FileException, IOException;

    /**
     * 远程下载
     * <p>使用任务</p>
     *
     * @param path 文件下载目录
     * @param name 文件名称
     * @param url 远程URL
     * @return 操作结果
     * @throws FileException 文件操作异常
     */
    boolean remoteDownload(String path, String name, String url) throws FileException;

    /**
     * 简易文件列表
     *
     * @param path 文件路径
     * @return 文件列表
     */
    List<FileListSimpleView> simpleLs(String path);

}

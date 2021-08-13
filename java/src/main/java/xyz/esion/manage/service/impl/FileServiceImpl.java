package xyz.esion.manage.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.service.FileService;
import xyz.esion.manage.view.FileInfoView;
import xyz.esion.manage.view.FileListSimpleView;
import xyz.esion.manage.view.FileListView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/7/21
 */
@Service
public class FileServiceImpl implements FileService {

    private final static Long UNIT = 1024L;
    private final static Integer MAX = 5;

    @Override
    public List<FileListView> ls(String path) throws FileException {
        if (!FileUtil.isDirectory(path)){
            throw new FileException("目录不是文件夹或不存在，" + path);
        }
        String command = "ls -l --full-time " + path;
        List<String> lines = RuntimeUtil.execForLines(command);
        return lines.stream().map(item -> FileListView.parse(path, item))
                .filter(Objects::nonNull).collect(Collectors.toList());
    }

    @Override
    public String open(String path, String charset) throws FileException {
        File file = FileUtil.file(path);
        if (!file.isFile()){
            throw new FileException("目录不是文件或不存在");
        }
        if (file.length() > UNIT * UNIT * MAX){
            throw new FileException("文件大于5M，请下载后打开");
        }
        if (StrUtil.isEmpty(charset)){
            charset = CharsetUtil.UTF_8;
        }
        return FileUtil.readString(file, charset);
    }

    @Override
    public byte[] show(String path) throws FileException {
        File file = FileUtil.file(path);
        if (!file.isFile()){
            throw new FileException("目录不是文件或不存在");
        }
        return IoUtil.readBytes(FileUtil.getInputStream(file));
    }

    @Override
    public byte[] download(String path) throws FileException {
        File file = FileUtil.file(path);
        if (!file.isDirectory()){
            throw new FileException("目录不是文件夹或不存在");
        }
        // 压缩文件夹到临时目录，文件名：试驾戳.zip
        // 将临时文件下载
        return new byte[0];
    }

    @Override
    public FileInfoView stat(String path) throws FileException {
        String command = "stat ";
        if (!FileUtil.exist(path)){
            throw new FileException("目标路径不存在，" + path);
        }
        return FileInfoView.parse(RuntimeUtil.execForLines(command + path));
    }

    @Override
    public boolean mv(List<String> paths, String target, boolean isForce) throws FileException {
        // 构建命令
        if (!FileUtil.isDirectory(target)){
            throw new FileException("目标路径不是目录或不存在，" + target);
        }
        String command = "mv ";
        if (isForce){
            command = command + "-f ";
        }
        command = command +  CollUtil.join(paths, " ") + " " + target;
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean cp(List<String> paths, String target) throws FileException {
        // 构建命令
        if (!FileUtil.isDirectory(target)){
            throw new FileException("目标路径不是目录或不存在，" + target);
        }
        String command = "cp -f -r -p ";
        command = command +  CollUtil.join(paths, " ") + " " + target;
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean rename(String path, String name, String target) throws FileException {
        String source = path + File.separator + name;
        if (!FileUtil.exist(source)){
            throw new FileException("源文件/夹不存在" + path);
        }
        target = path + File.separator + target;
        String command = "mv " + source + " " + target;
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean rm(List<String> paths, boolean isForce) throws FileException {
        String command = "rm -r ";
        if (isForce){
            command += "-f ";
        }
        command = command + CollUtil.join(paths, " ");
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean touch(String path, String name) throws FileException {
        if (!FileUtil.isDirectory(path)){
            throw new FileException("无法再非目录下创建文件，请检查路径是否正确，" + path);
        }
        String command = "touch " + path + File.separator + name;
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean mkdir(String path, String name) throws FileException {
        if (!FileUtil.isDirectory(path)){
            throw new FileException("无法再非目录下创建文件，请检查路径是否正确，" + path);
        }
        String command = "mkdir ";
        // 如果是多层目录，就使用递归
        if (name.indexOf("/") > 0 | name.indexOf("\\") > 0){
            command += "-p ";
        }
        command += path + File.separator + name;
        String result = RuntimeUtil.execForStr(command);
        if (!"".equals(result)){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean write(String path, String charset, String content) throws FileException {
        if (!FileUtil.isFile(path)){
            throw new FileException("目录不是文件，" + path);
        }
        if (StrUtil.isBlank(charset)){
            charset = CharsetUtil.UTF_8;
        }
        FileUtil.writeString(content, path, charset);
        return true;
    }

    @Override
    public boolean upload(String path, MultipartFile file) throws FileException, IOException {
        File filePath = FileUtil.file(path);
        if (!filePath.exists()){
            throw new FileException("路径不存在");
        }
        // 判断路径
        if (!filePath.isDirectory()){
            throw new FileException("路径不是文件夹，" + path);
        }
        String name = file.getOriginalFilename();
        if (StrUtil.isBlank(name)){
            name = String.valueOf(System.currentTimeMillis());
        }
        IoUtil.copy(file.getInputStream(), FileUtil.getOutputStream(new File(filePath, name)));
        return true;
    }

    @Override
    public boolean remoteDownload(String path, String name, String url) throws FileException {
        // 判断目录是否存在
        File file = FileUtil.file(path);
        if (!file.isDirectory()){
            throw new FileException("路径不是文件夹，" + path);
        }
        // 判断文件名
        if (StrUtil.isNotBlank(name)){
            file = new File(file, name);
        }
        // 获取文件
        /* 暂时直接下载 */
        HttpUtil.downloadFile(url, file);
        return true;
    }

    @Override
    public List<FileListSimpleView> simpleLs(String path) {
        if (StrUtil.isBlank(path)){
            return new ArrayList<>(0);
        }
        File file = FileUtil.file(path);
        File[] files = file.listFiles();
        if (files == null){
            return new ArrayList<>();
        }
        List<FileListSimpleView> result = new ArrayList<>();
        for (File item : files) {
            FileListSimpleView view = new FileListSimpleView();
            view.setName(item.getName());
            view.setPath(item.getPath());
            view.setIsDirectory(item.isDirectory());
            result.add(view);
        }
        return result;
    }

}

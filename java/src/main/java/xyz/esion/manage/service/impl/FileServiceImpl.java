package xyz.esion.manage.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.RuntimeUtil;
import cn.hutool.core.util.StrUtil;
import org.springframework.stereotype.Service;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.service.FileService;
import xyz.esion.manage.view.FileListView;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/7/21
 */
@Service
public class FileServiceImpl implements FileService {

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
    public boolean mv(List<String> paths, String target, boolean isForce) throws FileException {
        // 构建命令
        String[] source = toStringArray(paths);
        if (!FileUtil.isDirectory(target)){
            throw new FileException("目标路径不是目录或不存在，" + target);
        }
        String command = "mv ";
        if (isForce){
            command = command + "-f ";
        }
        command = command +  ArrayUtil.join(source, " ") + " " + target;
        String result = RuntimeUtil.execForStr(command);
        if (!result.equals("")){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean cp(List<String> paths, String target) throws FileException {
        // 构建命令
        String[] source = toStringArray(paths);
        if (!FileUtil.isDirectory(target)){
            throw new FileException("目标路径不是目录或不存在，" + target);
        }
        String command = "cp -f -r -p ";
        command = command +  ArrayUtil.join(source, " ") + " " + target;
        String result = RuntimeUtil.execForStr(command);
        if (!result.equals("")){
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
        if (!result.equals("")){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean rm(List<String> paths, boolean isForce) throws FileException {
        String[] source = toStringArray(paths);
        String command = "rm -r ";
        if (isForce){
            command += "-f ";
        }
        command = command + ArrayUtil.join(source, " ");
        String result = RuntimeUtil.execForStr(command);
        if (!result.equals("")){
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
        if (!result.equals("")){
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
        if (!result.equals("")){
            throw new FileException(result);
        }
        return true;
    }

    @Override
    public boolean write(String path, String content) throws FileException {
        if (!FileUtil.isFile(path)){
            throw new FileException("目录不是文件，" + path);
        }
        FileUtil.writeString(content, path, StandardCharsets.UTF_8);
        return true;
    }

    private String[] toStringArray(List<String> source) throws FileException {
        String[] target = new String[source.size()];
        for (int i = 0; i < source.size(); i++) {
            if (!FileUtil.exist(source.get(i))){
                throw new FileException("源路径不存在，" + source);
            }
            target[i] = source.get(i);
        }
        return target;
    }

}

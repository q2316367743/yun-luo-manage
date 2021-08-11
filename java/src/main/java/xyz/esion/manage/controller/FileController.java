package xyz.esion.manage.controller;

import cn.hutool.system.SystemUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.FileOption;
import xyz.esion.manage.service.FileService;

import java.io.IOException;

/**
 * @author Esion
 * @since 2021/7/24
 */
@RestController
@RequestMapping("api/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @GetMapping("ls")
    public Result ls(FileOption option) throws FileException {
        return Result.success().items(fileService.ls(option.getPath()));
    }

    @GetMapping("open")
    public Result open(FileOption option) throws FileException {
        return Result.success().item(fileService.open(option.getPath(), option.getCharset()));
    }

    @GetMapping("show")
    public ResponseEntity<byte[]> show(FileOption option) throws FileException {
        return ResponseEntity
                .status(200)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileService.show(option.getPath()));
    }

    @GetMapping("stat")
    public Result stat(FileOption option) throws FileException {
        return Result.success().items(fileService.stat(option.getPath()));
    }

    @PostMapping("mv")
    public Result mv(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.mv(option.getPaths(), option.getTarget(), option.getIsForce()));
    }

    @PostMapping("cp")
    public Result cp(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.cp(option.getPaths(), option.getTarget()));
    }

    @PostMapping("rename")
    public Result rename(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.rename(option.getPath(), option.getName(), option.getTarget()));
    }

    @PostMapping("rm")
    public Result rm(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.rm(option.getPaths(), option.getIsForce()));
    }

    @PostMapping("touch")
    public Result touch(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.touch(option.getPath(), option.getName()));
    }

    @PostMapping("mkdir")
    public Result mkdir(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.mkdir(option.getPath(), option.getName()));
    }

    @PostMapping("write")
    public Result write(@RequestBody FileOption option) throws FileException {
        return Result.choose(fileService.write(option.getPath(), option.getCharset(), option.getContent()));
    }

    @PostMapping("upload")
    public Result upload(FileOption option) throws FileException, IOException {
        return Result.choose(fileService.upload(option.getPath(), option.getFile()));
    }

    /**
     * 获取受支持的文件拓展名
     * */
    @GetMapping("base")
    public Result code(){
        return Result.success().item(SystemUtil.getUserInfo().getHomeDir());
    }

    @GetMapping("remote_download")
    public Result remoteDownload(FileOption option) throws FileException {
        return Result.choose(fileService.remoteDownload(option.getPath(), option.getName(), option.getUrl()));
    }

    @GetMapping("simple_ls")
    public Result simpleLs(String path){
        return Result.success().items(fileService.simpleLs(path));
    }

}

package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.exception.FileException;
import xyz.esion.manage.exception.ServerException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.FileOption;
import xyz.esion.manage.option.ServerCommandOption;
import xyz.esion.manage.option.ServerConfigOption;
import xyz.esion.manage.option.ServerOption;
import xyz.esion.manage.service.FileService;
import xyz.esion.manage.service.ServerService;

/**
 * @author Esion
 * @since 2021/7/31
 */
@RestController
@RequestMapping("api/server")
@RequiredArgsConstructor
public class ServerController {
    
    private final ServerService serverService;
    private final FileService fileService;

    @GetMapping("list")
    @SaCheckPermission("server&l")
    public Result list(){
        return Result.success().items(serverService.list());
    }

    @GetMapping("info/{id}")
    @SaCheckPermission("server&i")
    public Result infoById(@PathVariable("id") String id){
        return Result.success().item(serverService.infoById(id));
    }

    @GetMapping("install")
    public Result install(Integer serverType, Integer serverVersion, Integer installType){
        return Result.success().item(serverService.install(serverType, serverVersion, installType));
    }

    @PostMapping("add")
    @SaCheckPermission("server&a")
    public Result add(@RequestBody ServerOption option){
        option.setUserId(StpUtil.getLoginIdAsString());
        serverService.add(option);
        return Result.success();
    }

    @PostMapping("update/{id}")
    @SaCheckPermission("server&u")
    public Result update(@PathVariable("id") String id, @RequestBody ServerOption option){
        option.setUserId(StpUtil.getLoginIdAsString());
        serverService.update(id, option);
        return Result.success();
    }

    @PostMapping("remove/{id}")
    @SaCheckPermission("server&d")
    public Result remove(@PathVariable("id") String id){
        serverService.remove(id, StpUtil.getLoginIdAsString());
        return Result.success();
    }

    @PostMapping("command/add")
    @SaCheckPermission("server&u")
    public Result commandAdd(@RequestBody ServerCommandOption option){
        serverService.commandAdd(option);
        return Result.success();
    }

    @PostMapping("command/update/{id}")
    @SaCheckPermission("server&u")
    public Result commandUpdate(@PathVariable("id") String id, @RequestBody ServerCommandOption option){
        serverService.commandUpdate(id, option);
        return Result.success();
    }

    @PostMapping("command/remove/{id}")
    @SaCheckPermission("server&u")
    public Result commandRemove(@PathVariable("id") String id){
        serverService.commandRemove(id);
        return Result.success();
    }

    @GetMapping("command/exec/{id}")
    @SaCheckPermission("server&u")
    public Result execCommand(@PathVariable("id") String id) throws ServerException{
        return Result.success().item(serverService.commandExec(id));
    }

    @PostMapping("config/add")
    @SaCheckPermission("server&u")
    public Result configAdd(@RequestBody ServerConfigOption option){
        serverService.configAdd(option);
        return Result.success();
    }

    @PostMapping("config/update/{id}")
    @SaCheckPermission("server&u")
    public Result configUpdate(@PathVariable("id") String id, @RequestBody ServerConfigOption option){
        serverService.configUpdate(id, option);
        return Result.success();
    }

    @PostMapping("config/remove/{id}")
    @SaCheckPermission("server&u")
    public Result configRemove(@PathVariable("id") String id){
        serverService.configRemove(id);
        return Result.success();
    }

    @PostMapping("config/write")
    public Result write(@RequestBody FileOption option) throws FileException {
        option.verify();
        return Result.choose(fileService.write(option.getPath(), option.getCharset(), option.getContent()));
    }

}

package xyz.esion.manage.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.exception.ServerException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.ServerCommandOption;
import xyz.esion.manage.option.ServerConfigOption;
import xyz.esion.manage.option.ServerOption;
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

    @GetMapping("list")
    public Result list(){
        return Result.success().items(serverService.list());
    }

    @GetMapping("info/{id}")
    public Result infoById(@PathVariable("id") String id){
        return Result.success().item(serverService.infoById(id));
    }

    @GetMapping("install")
    public Result install(Integer serverType, Integer serverVersion, Integer installType){
        return Result.success().item(serverService.install(serverType, serverVersion, installType));
    }

    @PostMapping("add")
    public Result add(@RequestBody ServerOption option) throws ServerException{
        serverService.add(option);
        return Result.success();
    }

    @PostMapping("update/{id}")
    public Result update(@PathVariable("id") String id, @RequestBody ServerOption option) throws ServerException{
        serverService.update(id, option);
        return Result.success();
    }

    @PostMapping("remove/{id}")
    public Result remove(@PathVariable("id") String id){
        serverService.remove(id);
        return Result.success();
    }

    @PostMapping("command/update/{id}")
    public Result commandUpdate(@PathVariable("id") String id, @RequestBody ServerCommandOption option){
        serverService.commandUpdate(id, option);
        return Result.success();
    }

    @PostMapping("command/remove/{id}")
    public Result commandRemove(@PathVariable("id") String id){
        serverService.commandRemove(id);
        return Result.success();
    }

    @PostMapping("config/update/{id}")
    public Result configUpdate(@PathVariable("id") String id, @RequestBody ServerConfigOption option){
        serverService.configUpdate(id, option);
        return Result.success();
    }

    @PostMapping("config/remove/{id}")
    public Result configRemove(@PathVariable("id") String id){
        serverService.configRemove(id);
        return Result.success();
    }

    @GetMapping("config/info/{id}")
    public Result getConfigById(
            @PathVariable("id") String id,
            @RequestParam(required = false, defaultValue = "") String charset) throws ServerException{
        return Result.success().item(serverService.getConfigById(id, charset));
    }

    @GetMapping("command/exec/{id}")
    public Result execCommand(@PathVariable("id") String id) throws ServerException{
        return Result.success().item(serverService.execCommand(id));
    }
    
}
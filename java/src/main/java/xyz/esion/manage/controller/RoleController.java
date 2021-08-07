package xyz.esion.manage.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.RoleOption;
import xyz.esion.manage.service.RoleService;

/**
 * @author Esion
 * @since 2021/8/7
 */
@RestController
@RequestMapping("api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("list")
    public Result list(){
        return Result.success().items(roleService.list());
    }

    @GetMapping("simple")
    public Result simple(){
        return Result.success().items(roleService.simple());
    }

    @GetMapping("info/{id}")
    public Result info(@PathVariable String id){
        return Result.success().item(roleService.info(id));
    }

    @GetMapping("permissions")
    public Result permissions(){
        return Result.success().items(roleService.listPermission());
    }

    @PostMapping("add")
    public Result add(@RequestBody RoleOption option){
        option.setUserId(StpUtil.getLoginIdAsString());
        roleService.add(option);
        return Result.success();
    }

    @PostMapping("update/{id}")
    public Result update(@PathVariable String id, @RequestBody RoleOption option){
        option.setId(id);
        option.setUserId(StpUtil.getLoginIdAsString());
        roleService.update(option);
        return Result.success();
    }

    @PostMapping("remove/{id}")
    public Result remove(@PathVariable String id){
        roleService.remove(id, StpUtil.getLoginIdAsString());
        return Result.success();
    }

}

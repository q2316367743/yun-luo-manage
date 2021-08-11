package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
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
    @SaCheckPermission("system-role&l")
    public Result list(){
        return Result.success().items(roleService.list());
    }

    @GetMapping("info/{id}")
    @SaCheckPermission("system-role&l")
    public Result info(@PathVariable String id){
        return Result.success().item(roleService.info(id));
    }

    @PostMapping("add")
    @SaCheckPermission("system-role&a")
    public Result add(@RequestBody RoleOption option){
        option.setUserId(StpUtil.getLoginIdAsString());
        roleService.add(option);
        return Result.success();
    }

    @PostMapping("update/{id}")
    @SaCheckPermission("system-role&u")
    public Result update(@PathVariable String id, @RequestBody RoleOption option){
        option.setId(id);
        option.setUserId(StpUtil.getLoginIdAsString());
        roleService.update(option);
        return Result.success();
    }

    @PostMapping("remove/{id}")
    @SaCheckPermission("system-role&d")
    public Result remove(@PathVariable String id){
        roleService.remove(id, StpUtil.getLoginIdAsString());
        return Result.success();
    }

}

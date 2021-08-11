package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.UserOption;
import xyz.esion.manage.service.RoleService;
import xyz.esion.manage.service.UserService;

/**
 * @author Esion
 * @since 2021/8/8
 */
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final RoleService roleService;

    @GetMapping("page")
    @SaCheckPermission("system-user&l")
    public Result list(
            @RequestParam(value = "page", required = false, defaultValue = "1") Integer page,
            @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    ){
        return Result.success().page(userService.page(StpUtil.getLoginIdAsString(), page, size));
    }

    @GetMapping("role")
    @SaCheckPermission(value = {"system-user&a", "system-user&u"}, mode = SaMode.OR)
    public Result role(){
        return Result.success().items(roleService.simple());
    }

    @PostMapping("add")
    @SaCheckPermission("system-user&a")
    public Result add(@RequestBody UserOption option) throws UserException {
        option.setUserId(StpUtil.getLoginIdAsString());
        userService.add(option);
        return Result.success();
    }

    @PostMapping("update/{id}")
    @SaCheckPermission("system-user&u")
    public Result update(@PathVariable String id, @RequestBody UserOption option) {
        option.setId(id);
        option.setUserId(StpUtil.getLoginIdAsString());
        userService.update(option);
        return Result.success();
    }

    @PostMapping("reset_password/{id}")
    @SaCheckPermission("system-user&u")
    public Result resetPassword(@PathVariable String id){
        userService.resetPassword(id, StpUtil.getLoginIdAsString());
        return Result.success();
    }

    @PostMapping("remove/{id}")
    @SaCheckPermission("system-user&d")
    public Result remove(@PathVariable String id){
        userService.remove(id, StpUtil.getLoginIdAsString());
        return Result.success();
    }

}

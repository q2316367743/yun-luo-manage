package xyz.esion.manage.controller;

import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.service.UserService;

/**
 * @author Esion
 * @since 2021/7/27
 */
@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("login")
    public Result login(String username, String password){
        if (userService.login(username, password)){
            StpUtil.setLoginId(username);
            String token = StpUtil.getTokenValue();
            return Result.success().item(token);
        }else {
            return Result.fail().message("登录错误");
        }
    }

    @GetMapping("logout")
    public Result logout(){
        StpUtil.logout();
        return Result.success();
    }

}

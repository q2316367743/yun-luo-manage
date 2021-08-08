package xyz.esion.manage.controller;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.option.CommonOption;
import xyz.esion.manage.service.CommonService;
import xyz.esion.manage.view.UserView;

/**
 * @author Esion
 * @since 2021/7/27
 */
@RestController
@RequestMapping("api/common")
@RequiredArgsConstructor
public class CommonController {

    private final CommonService commonService;

    @GetMapping("login")
    public Result login(String username, String password){
        UserView view = commonService.login(username, password);
        if (view == null){
            return Result.fail().message("用户或密码错误");
        }else {
            StpUtil.setLoginId(view.getId());
            String token = StpUtil.getTokenValue();
            view.setToken(token);
            return Result.success().item(view);
        }
    }

    @GetMapping("logout")
    public Result logout(){
        StpUtil.logout();
        return Result.success();
    }

    @GetMapping("info")
    public Result permission(){
        return Result.success().item(commonService.info(StpUtil.getLoginIdAsString()));
    }

    @PostMapping("update")
    public Result update(@RequestBody CommonOption option) throws UserException {
        String username = option.getUsername();
        String password = option.getPassword();
        String old = option.getOld();
        if (StrUtil.isBlank(password) && StrUtil.isBlank(old)){
            if (StrUtil.isBlank(username)){
                throw new IllegalArgumentException("参数缺失");
            }
        }
        option.setUserId(StpUtil.getLoginIdAsString());
        commonService.update(option);
        StpUtil.logout();
        return Result.success();
    }

}

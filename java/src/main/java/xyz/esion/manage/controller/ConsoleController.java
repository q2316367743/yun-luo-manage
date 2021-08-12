package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.util.RuntimeUtil;
import org.springframework.web.bind.annotation.*;
import xyz.esion.manage.global.Result;

/**
 * 命令行接口
 *
 * @author Esion
 * @since 2021/7/16
 */
@RestController
@RequestMapping("api/console")
@SaCheckLogin
public class ConsoleController {

    @PostMapping("execForStr")
    public Result execForStr(@RequestBody String command){
        return Result.success().item(RuntimeUtil.execForStr(command));
    }

    @PostMapping("execForLines")
    public Result execForLines(@RequestBody String command){
        return Result.success().items(RuntimeUtil.execForLines(command));
    }

}

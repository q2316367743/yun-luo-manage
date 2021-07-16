package xyz.esion.manage.controller;

import cn.hutool.core.util.RuntimeUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import xyz.esion.manage.global.Result;

/**
 * 命令行接口
 *
 * @author qiao shengda
 * @since 2021/7/16
 */
@RestController
@RequestMapping("api/console")
public class RuntimeController {

    @GetMapping("execForStr")
    public Result execForStr(String command){
        return Result.success().item(RuntimeUtil.execForStr(command));
    }

    @GetMapping("execForLines")
    public Result execForLines(String command){
        return Result.success().items(RuntimeUtil.execForLines(command));
    }

}

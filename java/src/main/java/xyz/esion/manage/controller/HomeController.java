package xyz.esion.manage.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.system.oshi.OshiUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.hardware.GlobalMemory;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.service.DatabaseService;

/**
 * 系统服务
 *
 * @author Esion
 * @since 2021/7/15
 */
@RestController
@RequestMapping("api/home")
@RequiredArgsConstructor
public class HomeController {

    private final DatabaseService databaseService;

    @GetMapping("dynamic")
    public Result get(){
        JSONObject item = new JSONObject();
        JSONObject memory = new JSONObject();
        GlobalMemory globalMemory = OshiUtil.getMemory();
        memory.set("total", globalMemory.getTotal());
        memory.set("available", globalMemory.getAvailable());
        item.set("memory", memory);
        item.set("cpu", OshiUtil.getCpuInfo());
        return Result.success().item(item);
    }

    @GetMapping("base")
    public Result base(){
        JSONObject base = new JSONObject();
        base.set("os", OshiUtil.getOs());
        base.set("database", databaseService.count());
        base.set("redis", 0);
        return Result.success().item(base);
    }

}

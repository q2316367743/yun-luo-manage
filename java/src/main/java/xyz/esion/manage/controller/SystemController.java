package xyz.esion.manage.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.system.oshi.OshiUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import xyz.esion.manage.global.Constant;
import xyz.esion.manage.global.Result;
import xyz.esion.manage.service.DatabaseService;

import java.io.File;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/**
 * 系统服务
 *
 * @author Esion
 * @since 2021/7/15
 */
@RestController
@RequestMapping("api/system")
public class SystemController {

    private DatabaseService databaseService;

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

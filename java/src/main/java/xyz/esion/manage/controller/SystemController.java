package xyz.esion.manage.controller;

import cn.hutool.json.JSONObject;
import cn.hutool.system.oshi.OshiUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import xyz.esion.manage.global.Constant;
import xyz.esion.manage.global.Result;

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

    @RequestMapping
    public Result get(){
        JSONObject item = new JSONObject();
        item.set("cup", OshiUtil.getCpuInfo());
        item.set("hardware", OshiUtil.getHardware());
        item.set("os", OshiUtil.getOs());
        item.set("memory", OshiUtil.getMemory());
        item.set("processor", OshiUtil.getProcessor());
        item.set("disk", OshiUtil.getHardware().getDiskStores());
        item.set("network", OshiUtil.getHardware().getNetworkIFs());
        return Result.success().item(item);
    }


}

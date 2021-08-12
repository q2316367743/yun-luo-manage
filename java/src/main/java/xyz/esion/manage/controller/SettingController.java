package xyz.esion.manage.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 设置
 *
 * @author Esion
 * @since 2021/7/28
 */
@RestController
@RequestMapping("api/setting")
@SaCheckLogin
public class SettingController {
}

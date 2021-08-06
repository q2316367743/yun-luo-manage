package xyz.esion.manage.config;

import cn.dev33.satoken.stp.StpInterface;

import java.util.List;

/**
 * 权限配置
 *
 * @author Esion
 * @since 2021/8/6
 */
public class StpPermissionConfig implements StpInterface {

    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        return null;
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        return null;
    }

}

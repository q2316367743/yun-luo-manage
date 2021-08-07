package xyz.esion.manage.service;

import xyz.esion.manage.option.RoleOption;
import xyz.esion.manage.view.PermissionsView;
import xyz.esion.manage.view.RoleInfoView;
import xyz.esion.manage.view.RoleListView;
import xyz.esion.manage.view.RoleSimpleVie;

import java.util.List;

/**
 * @author Esion
 * @since 2021/8/7
 */
public interface RoleService {

    /**
     * 获取全部的角色
     *
     * @return 角色列表
     */
    List<RoleListView> list();

    /**
     * 获取简洁的角色列表
     * @return 简洁的角色列表
     */
    List<RoleSimpleVie> simple();

    /**
     * 获取全部权限
     *
     * @return 全部权限
     */
    List<PermissionsView> listPermission();

    /**
     * 根据角色ID获取信息
     *
     * @param id 角色ID
     * @return 详细信息
     */
    RoleInfoView info(String id);

    /**
     * 新增角色
     *
     * @param option 角色信息
     */
    void add(RoleOption option);

    /**
     * 更新角色
     *
     * @param option 角色信息
     */
    void update(RoleOption option);

    /**
     * 删除角色
     *
     * @param id 角色ID
     * @param userId 操作人ID
     */
    void remove(String id, String userId);

}

package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xyz.esion.manage.entity.Permission;
import xyz.esion.manage.entity.PermissionCategory;
import xyz.esion.manage.entity.Role;
import xyz.esion.manage.entity.RolePermission;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.mapper.PermissionCategoryMapper;
import xyz.esion.manage.mapper.PermissionMapper;
import xyz.esion.manage.mapper.RoleMapper;
import xyz.esion.manage.mapper.RolePermissionMapper;
import xyz.esion.manage.option.RoleOption;
import xyz.esion.manage.service.RoleService;
import xyz.esion.manage.view.PermissionsView;
import xyz.esion.manage.view.RoleInfoView;
import xyz.esion.manage.view.RoleListView;
import xyz.esion.manage.view.RoleSimpleVie;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 
 * @author Esion
 * @since 2021/8/7
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleMapper roleMapper;
    private final PermissionCategoryMapper permissionCategoryMapper;
    private final PermissionMapper permissionMapper;
    private final RolePermissionMapper rolePermissionMapper;

    @Override
    public List<RoleListView> list() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("is_delete", 0));
        return roles.stream().map(item -> BeanUtil.copyProperties(item, RoleListView.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<RoleSimpleVie> simple() {
        List<Role> roles = roleMapper.selectList(new QueryWrapper<Role>().eq("is_delete", 0));
        return roles.stream().map(item -> BeanUtil.copyProperties(item, RoleSimpleVie.class))
                .collect(Collectors.toList());
    }

    @Override
    public List<PermissionsView> listPermission() {
        List<PermissionCategory> permissionCategories = permissionCategoryMapper
                .selectList(new QueryWrapper<>());
        List<Permission> permissions = permissionMapper.selectList(new QueryWrapper<>());
        Map<String, List<Permission>> permissionMap = new HashMap<>();
        for (Permission permission : permissions) {
            if (permissionMap.containsKey(permission.getCategoryId())){
                permissionMap.get(permission.getCategoryId()).add(permission);
            }else {
                List<Permission> temp = new ArrayList<>();
                temp.add(permission);
                permissionMap.put(permission.getCategoryId(), temp);
            }
        }
        List<PermissionsView> views = new ArrayList<>();
        for (PermissionCategory permissionCategory : permissionCategories) {
            PermissionsView view = new PermissionsView();
            view.setName(permissionCategory.getName());
            view.setPermissions(new LinkedList<>());
            List<Permission> temps = permissionMap.get(permissionCategory.getId());
            if (temps != null){
                view.getPermissions().addAll(temps.stream()
                        .map(item -> BeanUtil.copyProperties(item, PermissionsView.PermissionView.class))
                        .collect(Collectors.toList()));
            }
            views.add(view);
        }
        return views;
    }

    @Override
    public RoleInfoView info(String id) {
        Role role = roleMapper.selectById(id);
        if (role == null){
            return null;
        }
        List<RolePermission> rolePermissions = rolePermissionMapper
                .selectList(new QueryWrapper<RolePermission>().eq("role_id", id));
        RoleInfoView view = BeanUtil.copyProperties(role, RoleInfoView.class);
        view.setPermissions(rolePermissions.stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList()));
        return view;
    }

    @Override
    @Transactional(rollbackFor = UserException.class)
    public void add(RoleOption option) {
        Role role = new Role();
        role.setName(option.getName());
        role.setValue(option.getValue());
        role.setCreateId(option.getUserId());
        role.setUpdateId(option.getUserId());
        roleMapper.insert(role);
        // 增加权限
        for (String permissionId : option.getPermissionIds()) {
            RolePermission temp = new RolePermission();
            temp.setRoleId(role.getId());
            temp.setPermissionId(permissionId);
            rolePermissionMapper.insert(temp);
        }
    }

    @Override
    @Transactional(rollbackFor = UserException.class)
    public void update(RoleOption option) {
        Role role = new Role();
        role.setId(option.getId());
        role.setName(option.getName());
        role.setValue(option.getValue());
        role.setUpdateId(option.getUserId());
        roleMapper.updateById(role);
        List<String> permissionIds = option.getPermissionIds();
        List<String> deleteIds = new LinkedList<>();
        List<String> addIds = new LinkedList<>(permissionIds);
        // 获取全部权限
        List<RolePermission> rolePermissions = rolePermissionMapper
                .selectList(new QueryWrapper<RolePermission>()
                .eq("role_id", option.getId()));
        for (RolePermission rolePermission : rolePermissions) {
            addIds.remove(rolePermission.getPermissionId());
            if (!permissionIds.contains(rolePermission.getPermissionId())){
                // 删除
                deleteIds.add(rolePermission.getId());
            }
        }
        // 删除
        rolePermissionMapper.deleteBatchIds(deleteIds);
        for (String addId : addIds) {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(option.getId());
            rolePermission.setPermissionId(addId);
            rolePermissionMapper.insert(rolePermission);
        }
    }

    @Override
    public void remove(String id, String userId) {
        Role role = new Role();
        role.setId(id);
        role.setUpdateId(userId);
        role.setIsDelete(1);
        roleMapper.updateById(role);
    }

}

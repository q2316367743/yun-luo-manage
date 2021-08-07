package xyz.esion.manage.config;

import cn.dev33.satoken.stp.StpInterface;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import xyz.esion.manage.entity.Role;
import xyz.esion.manage.entity.User;
import xyz.esion.manage.entity.UserPermission;
import xyz.esion.manage.mapper.RoleMapper;
import xyz.esion.manage.mapper.UserMapper;
import xyz.esion.manage.mapper.UserPermissionMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 权限配置
 *
 * @author Esion
 * @since 2021/8/6
 */
@Component
@RequiredArgsConstructor
public class StpPermissionConfig implements StpInterface {

    private final UserPermissionMapper userPermissionMapper;
    private final UserMapper userMapper;
    private final RoleMapper roleMapper;

    @Override
    public List<String> getPermissionList(Object loginId, String loginKey) {
        return userPermissionMapper.selectList(new QueryWrapper<UserPermission>()
                .eq("user_id", loginId))
                .stream()
                .map(UserPermission::getPermissionValue)
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginKey) {
        User user = userMapper.selectById(loginId.toString());
        Role role = roleMapper.selectById(user.getRoleId());
        // 暂时不对角色进行验证
        return Collections.singletonList(role.getValue());
    }

}

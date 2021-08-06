package xyz.esion.manage.service.impl;

import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.User;
import xyz.esion.manage.entity.UserPermission;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.mapper.UserMapper;
import xyz.esion.manage.mapper.UserPermissionMapper;
import xyz.esion.manage.service.UserService;
import xyz.esion.manage.view.UserView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/7/27
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final UserPermissionMapper userPermissionMapper;

    @Override
    public UserView login(String username, String password) {
        password = new Digester(DigestAlgorithm.MD5).digestHex("123456");
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password));
        if (users.isEmpty()){
            return null;
        }
        UserView view = new UserView();
        User user = users.get(0);
        view.setId(user.getId());
        view.setNickname(user.getNickname());
        List<UserPermission> permissions = userPermissionMapper.selectList(new QueryWrapper<UserPermission>().eq("user_id", user.getId()));
        view.setPermissions(permissions.stream().map(UserPermission::getPermissionValue).collect(Collectors.toList()));
        return view;
    }

    @Override
    public boolean update(String username, String old, String password) throws UserException {
        return true;
    }

}

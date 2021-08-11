package xyz.esion.manage.service.impl;

import cn.hutool.core.util.StrUtil;
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
import xyz.esion.manage.option.CommonOption;
import xyz.esion.manage.service.CommonService;
import xyz.esion.manage.view.UserView;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/7/27
 */
@Service
@RequiredArgsConstructor
public class CommonServiceImpl implements CommonService {

    private final UserMapper userMapper;
    private final UserPermissionMapper userPermissionMapper;

    @Override
    public UserView login(String username, String password) {
        password = new Digester(DigestAlgorithm.MD5).digestHex(password);
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .eq("username", username)
                .eq("password", password)
                .eq("is_delete", 0)
                .eq("is_enable", 1));
        if (users.isEmpty()){
            return null;
        }
        UserView view = new UserView();
        User user = users.get(0);
        view.setId(user.getId());
        view.setNickname(user.getNickname());
        view.setPermissions(userPermissionMapper.selectList(
                new QueryWrapper<UserPermission>()
                        .eq("user_id", user.getId()))
                .stream().map(UserPermission::getPermissionValue)
                .collect(Collectors.toList()));
        return view;
    }

    @Override
    public UserView info(String id) {
        UserView view = new UserView();
        User user = userMapper.selectById(id);
        view.setNickname(user.getNickname());
        view.setPermissions(userPermissionMapper.selectList(
                        new QueryWrapper<UserPermission>()
                                .eq("user_id", user.getId()))
                .stream().map(UserPermission::getPermissionValue)
                .collect(Collectors.toList()));
        return view;
    }

    @Override
    public boolean update(CommonOption option) throws UserException {
        // 获取密码，对比
        User user = userMapper.selectById(option.getUserId());
        if (!user.getPassword().equals(new Digester(DigestAlgorithm.MD5).digestHex(option.getOld()))){
            throw new UserException("旧密码错误");
        }
        User temp = new User();
        temp.setId(option.getUserId());
        temp.setUsername(StrUtil.isBlank(option.getUsername()) ? null : option.getUsername());
        temp.setPassword(new Digester(DigestAlgorithm.MD5).digestHex(option.getPassword()));
        return userMapper.updateById(temp) > 0;
    }

}

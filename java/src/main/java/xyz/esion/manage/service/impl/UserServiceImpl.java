package xyz.esion.manage.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.digest.DigestAlgorithm;
import cn.hutool.crypto.digest.Digester;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.User;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.mapper.UserMapper;
import xyz.esion.manage.option.UserOption;
import xyz.esion.manage.service.UserService;
import xyz.esion.manage.view.UserListView;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public Page<UserListView> page(String userId, Integer page, Integer size) {
        Page<User> userPage = userMapper.selectPage(new Page<>(page, size), new QueryWrapper<User>()
                .ne("id", userId)
                .eq("is_delete", 0));
        Page<UserListView> result = new Page<>();
        result.setTotal(userPage.getTotal());
        List<UserListView> users = userPage.getRecords().stream().map(user -> {
            UserListView view = new UserListView();
            view.setId(user.getId());
            view.setUsername(user.getUsername());
            view.setNickname(user.getNickname());
            view.setRoleId(user.getRoleId());
            view.setIsEnable(user.getIsEnable() == 1);
            return view;
        }).collect(Collectors.toList());
        result.setRecords(users);
        return result;
    }

    @Override
    public void add(UserOption option) throws UserException {
        // 查询是否有相同用户名
        Integer integer = userMapper.selectCount(new QueryWrapper<User>()
                .eq("username", option.getUsername())
                .eq("is_delete", 0));
        if (integer > 0){
            throw new UserException("用户名重复，请更换用户名");
        }
        User user = new User();
        user.setUsername(option.getUsername());
        user.setPassword(new Digester(DigestAlgorithm.MD5).digestHex(option.getPassword()));
        user.setNickname(option.getNickname());
        user.setRoleId(option.getRoleId());
        user.setCreateId(option.getUserId());
        user.setUpdateId(option.getRoleId());
        user.setIsDelete(0);
        user.setIsEnable(1);
        userMapper.insert(user);
    }

    @Override
    public void update(UserOption option) {
        User user = new User();
        user.setId(option.getId());
        if (StrUtil.isNotBlank(option.getRoleId())){
            user.setRoleId(option.getRoleId());
        }
        if (option.getIsEnable() != null){
            user.setIsEnable(option.getIsEnable() ? 1 : 0);
        }
        user.setUpdateId(option.getUserId());
        userMapper.updateById(user);
    }

    @Override
    public void resetPassword(String id, String userId) {
        User user = new User();
        user.setId(id);
        user.setPassword(new Digester(DigestAlgorithm.MD5).digestHex("123456"));
        user.setUpdateId(userId);
        userMapper.updateById(user);
    }

    @Override
    public void remove(String id, String userId) {
        User user = new User();
        user.setId(id);
        user.setIsDelete(1);
        user.setUpdateId(userId);
        userMapper.updateById(user);
    }

}

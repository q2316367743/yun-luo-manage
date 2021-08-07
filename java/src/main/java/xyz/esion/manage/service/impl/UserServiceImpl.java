package xyz.esion.manage.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import xyz.esion.manage.entity.User;
import xyz.esion.manage.mapper.UserMapper;
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
    public List<UserListView> list(String userId) {
        List<User> users = userMapper.selectList(new QueryWrapper<User>()
                .ne("id", userId)
                .eq("is_delete", 0)
                .eq("is_enable", 1));
        return users.stream().map(user -> {
            UserListView view = new UserListView();
            view.setId(user.getId());
            view.setUsername(user.getUsername());
            view.setNickname(user.getNickname());
            view.setRoleId(user.getRoleId());
            return view;
        }).collect(Collectors.toList());
    }

}

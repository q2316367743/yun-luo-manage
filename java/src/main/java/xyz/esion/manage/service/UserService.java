package xyz.esion.manage.service;

import xyz.esion.manage.view.UserListView;

import java.util.List;

/**
 * @author Esion
 * @since 2021/8/7
 */
public interface UserService {

    /**
     * 获取除了自己的其他用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    List<UserListView> list(String userId);

}

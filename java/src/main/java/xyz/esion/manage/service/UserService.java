package xyz.esion.manage.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.option.UserOption;
import xyz.esion.manage.view.user.UserListView;

/**
 * @author Esion
 * @since 2021/8/7
 */
public interface UserService {

    /**
     * 获取除了自己的其他用户信息
     *
     * @param userId 用户ID
     * @param page 页码
     * @param size 每页数目
     * @return 用户信息
     */
    Page<UserListView> page(String userId, Integer page, Integer size);

    /**
     * 新增
     *
     * @param option 用户信息
     */
    void add(UserOption option) throws UserException;

    /**
     * 通用修改，<em>目前只能修改角色</em>
     *
     * @param option 跟新内容，为null则不更新
     * @throws UserException 用户不存在
     */
    void update(UserOption option);

    /**
     * 重置密码，重置为123456
     *
     * @param id 重置ID
     * @param userId 操作人ID
     */
    void resetPassword(String id, String userId);

    /**
     * 删除用户
     *
     * @param id 用户ID
     * @param userId 操作人ID
     */
    void remove(String id, String userId);

}

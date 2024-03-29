package xyz.esion.manage.service;

import xyz.esion.manage.exception.UserException;
import xyz.esion.manage.option.CommonOption;
import xyz.esion.manage.view.user.UserView;

/**
 * 用户登录
 *
 * @author Esion
 * @since 2021/7/27
 */
public interface CommonService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    UserView login(String username, String password);

    /**
     * 通过用户ID获取用户信息
     *
     * @param id 用户ID
     * @return 用户信息
     */
    UserView info(String id);

    /**
     * 修改
     *
     * @param option 用户修改信息
     * @return 修改结果
     * @throws UserException 旧密码错误
     */
    boolean update(CommonOption option) throws UserException;

}

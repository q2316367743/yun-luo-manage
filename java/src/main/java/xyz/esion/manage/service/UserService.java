package xyz.esion.manage.service;

/**
 * 用户登录
 *
 * @author Esion
 * @since 2021/7/27
 */
public interface UserService {

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    boolean login(String username, String password);

    /**
     * 修改
     *
     * @param username 用户名
     * @param password 密码
     * @return 修改结果
     */
    boolean update(String username, String password);

}

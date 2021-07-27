package xyz.esion.manage.service.impl;

import org.springframework.stereotype.Service;
import xyz.esion.manage.service.UserService;

/**
 * @author Esion
 * @since 2021/7/27
 */
@Service
public class UserServiceImpl implements UserService {

    private String username;
    private String password;

    public UserServiceImpl() {
        // 初始化用户名和密码
        this.username = "Esion";
        this.password = "123456";
    }

    @Override
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public boolean update(String username, String password) {
        this.username = username;
        this.password = password;
        return true;
    }

}

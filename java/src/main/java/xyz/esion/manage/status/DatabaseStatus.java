package xyz.esion.manage.status;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.sql.DataSource;

/**
 * @author Esion
 * @since 2021/7/17
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DatabaseStatus {

    /**
     * 数据源
     * */
    private DataSource dataSource;

    /**
     * 状态
     * */
    private Integer status;

    /**
     * 连接字符串
     * */
    private String url;

    /**
     * 用户名
     * */
    private String username;

    /**
     * 密码
     * */
    private String password;

}

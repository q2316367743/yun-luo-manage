package xyz.esion.manage.view.user;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户列表
 *
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class UserListView implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    private String username;

    private String nickname;

    private String roleId;

    private Boolean isEnable;

}

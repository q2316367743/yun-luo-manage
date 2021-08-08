package xyz.esion.manage.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/8/8
 */
@Data
public class UserOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String id;

    /**
     * 仅增加时有效
     */
    private String username;

    /**
     * 修改密码 - 新密码
     */
    private String password;

    /**
     * 仅增加时有效
     */
    private String nickname;

    /**
     * 是否启用
     */
    private Boolean isEnable;

    /**
     * 新角色
     */
    private String roleId;

    /**
     * 操作人ID
     */
    @JsonIgnore
    private String userId;

}

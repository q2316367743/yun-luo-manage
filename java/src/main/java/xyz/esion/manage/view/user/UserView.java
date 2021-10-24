package xyz.esion.manage.view.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Esion
 * @since 2021/8/6
 */
@Data
public class UserView implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String id;

    /**
     *
     */
    private String nickname;

    /**
     * token
     */
    private String token;

    /**
     * 权限合集
     */
    private List<String> permissions;

}

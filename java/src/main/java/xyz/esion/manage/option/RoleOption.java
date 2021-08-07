package xyz.esion.manage.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class RoleOption implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonIgnore
    private String id;

    /**
     *
     */
    private String name;

    /**
     *
     */
    private String value;

    /**
     * 权限列表
     */
    private List<String> permissionIds;

    @JsonIgnore
    private String userId;

}

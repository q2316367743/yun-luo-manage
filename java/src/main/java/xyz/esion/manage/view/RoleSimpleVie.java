package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class RoleSimpleVie  implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 角色名称
     */
    private String name;

}

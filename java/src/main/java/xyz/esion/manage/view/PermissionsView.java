package xyz.esion.manage.view;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author Esion
 * @since 2021/8/7
 */
@Data
public class PermissionsView implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 权限分类名称
     */
    private String name;

    private List<PermissionView> permissions;

    @Data
    public static class PermissionView implements Serializable {

        private static final long serialVersionUID = 1L;

        private String id;

        private String name;

    }

}

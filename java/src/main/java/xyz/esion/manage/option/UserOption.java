package xyz.esion.manage.option;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Esion
 * @since 2021/7/28
 */
@Data
public class UserOption implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;

    private String password;

}

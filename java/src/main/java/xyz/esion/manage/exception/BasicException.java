package xyz.esion.manage.exception;

import lombok.Data;

/**
 * @author Esion
 * @since 2021/7/24
 */
@Data
public class BasicException extends Exception {

    private String message;

    public BasicException(String message) {
        this.message = message;
    }
}

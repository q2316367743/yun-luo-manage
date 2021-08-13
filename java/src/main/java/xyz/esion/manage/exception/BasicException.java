package xyz.esion.manage.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Esion
 * @since 2021/7/24
 */
@Setter
@Getter
public class BasicException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1762042697201451288L;
	private String message;

    public BasicException(String message) {
        this.message = message;
    }
}

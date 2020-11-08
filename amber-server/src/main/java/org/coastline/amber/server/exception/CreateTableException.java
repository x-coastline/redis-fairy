package org.coastline.amber.server.exception;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class CreateTableException extends RuntimeException {

    private int code;

    private String message;

    public CreateTableException() {
        super();
    }

    public CreateTableException(String message) {
        super(message);
    }

    public CreateTableException(String message, Throwable cause) {
        super(cause);
        this.message = message;
    }
}

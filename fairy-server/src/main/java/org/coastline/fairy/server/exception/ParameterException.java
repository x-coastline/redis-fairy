package org.coastline.fairy.server.exception;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class ParameterException extends RuntimeException {

    private int code;

    private String message;

    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }
}

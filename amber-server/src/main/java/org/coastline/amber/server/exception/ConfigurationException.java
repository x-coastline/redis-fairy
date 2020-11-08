package org.coastline.amber.server.exception;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class ConfigurationException extends RuntimeException {

    private int code;

    private String message;

    public ConfigurationException() {
        super();
    }

    public ConfigurationException(String message) {
        super(message);
    }
}

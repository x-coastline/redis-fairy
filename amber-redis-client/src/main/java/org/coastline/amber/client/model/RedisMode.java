package org.coastline.amber.client.model;

import java.util.Objects;

/**
 * @author Jay.H.Zou
 * @date 2020/11/2
 */
public enum  RedisMode {

    CLUSTER("cluster"),

    STANDALONE("standalone"),

    SENTINEL("sentinel");

    private String value;

    RedisMode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static RedisMode getRedisMode(String value) {
        for (RedisMode redisMode : values()) {
            if (Objects.equals(redisMode.getValue(), value)) {
                return redisMode;
            }
        }
        return null;
    }
}

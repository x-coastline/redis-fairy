package org.coastline.fairy.client.model;

import java.util.Objects;

/**
 * @author Jay.H.Zou
 * @date 2020/11/2
 */
public enum RedisMode {

    /**
     * cluster
     */
    CLUSTER("cluster"),

    /**
     * master-replica
     */
    STANDALONE("standalone"),

    /**
     * sentinel
     */
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

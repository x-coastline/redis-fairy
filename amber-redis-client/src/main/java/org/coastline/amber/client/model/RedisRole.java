package org.coastline.amber.client.model;

/**
 * @author Jay.H.Zou
 * @date 2020/10/31
 */
public enum RedisRole {
    /**
     * master
     */
    MASTER,

    /**
     * replica
     */
    REPLICA,

    /**
     * sentinel
     */
    SENTINEL
}

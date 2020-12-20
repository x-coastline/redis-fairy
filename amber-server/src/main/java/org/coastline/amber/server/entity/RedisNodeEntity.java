package org.coastline.amber.server.entity;

import org.coastline.amber.client.model.RedisNode;

/**
 * @author Jay.H.Zou
 * @date 2020/11/8
 */
public class RedisNodeEntity extends RedisNode {

    /**
     * database primary key
     */
    private Integer redisNodeId;

    private String host;

    private Integer port;

    public Integer getRedisNodeId() {
        return redisNodeId;
    }

    public void setRedisNodeId(Integer redisNodeId) {
        this.redisNodeId = redisNodeId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}

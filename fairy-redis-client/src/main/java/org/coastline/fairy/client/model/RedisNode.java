package org.coastline.fairy.client.model;

import redis.clients.jedis.HostAndPort;

/**
 * @author Jay.H.Zou
 * @date 2020/11/3
 */
public class RedisNode {

    private String nodeId;

    private HostAndPort node;

    private RedisRole role;

    public RedisNode() {
    }

    public RedisNode(String nodeId, HostAndPort node) {
        this.nodeId = nodeId;
        this.node = node;
    }

    public String getNodeId() {
        return nodeId;
    }

    public void setNodeId(String nodeId) {
        this.nodeId = nodeId;
    }

    public HostAndPort getNode() {
        return node;
    }

    public void setNode(HostAndPort node) {
        this.node = node;
    }

    public RedisRole getRole() {
        return role;
    }

    public void setRole(RedisRole role) {
        this.role = role;
    }
}

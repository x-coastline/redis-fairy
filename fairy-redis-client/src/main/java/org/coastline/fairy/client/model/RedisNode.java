package org.coastline.fairy.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.HostAndPort;

/**
 * base class for redis node
 *
 * @author Jay.H.Zou
 * @date 2020/11/3
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisNode {

    private String nodeId;

    private HostAndPort node;

    private RedisRole role;

    public RedisNode(String nodeId, HostAndPort node) {
        this.nodeId = nodeId;
        this.node = node;
    }

}

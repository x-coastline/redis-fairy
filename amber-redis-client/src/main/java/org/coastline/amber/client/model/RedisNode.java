package org.coastline.amber.client.model;

import redis.clients.jedis.HostAndPort;

/**
 * @author Jay.H.Zou
 * @date 2020/11/3
 */
public class RedisNode {

    private String nodeId;

    private HostAndPort node;

    private RedisRole role;

}

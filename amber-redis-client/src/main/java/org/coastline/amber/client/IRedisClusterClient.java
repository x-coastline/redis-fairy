package org.coastline.amber.client;

import redis.clients.jedis.JedisCluster;

/**
 * @author Jay.H.Zou
 * @date 2019/7/18
 */
public interface IRedisClusterClient extends IDatabaseCommand {

    JedisCluster getRedisClusterClient();

    void close();
}

package org.coastline.fairy.client;

import org.coastline.fairy.client.model.RedisNode;
import org.coastline.fairy.client.model.RedisURI;
import org.coastline.fairy.common.StringUtil;
import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.util.HashSet;
import java.util.Set;

/**
 * Build redis client
 *
 * @author Jay.H.Zou
 * @date 7/18/2019
 */
public class RedisClientFactory {

    public static RedisClient buildRedisClient(RedisURI redisURI) {
        RedisClient redisClient = new RedisClient(redisURI);
        if (redisClient.getClient() == null) {
            throw new JedisConnectionException("All seed node can't connect.");
        }
        return redisClient;
    }

    public static RedisClient buildRedisClient(RedisNode redisNode, String requirePass) {
        HostAndPort node = redisNode.getNode();
        RedisURI redisURI = new RedisURI(node.getHost(), node.getPort(), requirePass);
        return buildRedisClient(redisURI);
    }

    public static RedisClient buildRedisClient(HostAndPort hostAndPort, String requirePass) {
        RedisURI redisURI = new RedisURI(hostAndPort, requirePass);
        return buildRedisClient(redisURI);
    }

    public static RedisClient buildRedisClient(HostAndPort hostAndPort) {
        return buildRedisClient(hostAndPort, null);
    }

    public static RedisClient buildRedisClient(String host, int port) {
        return buildRedisClient(new HostAndPort(host, port));
    }

    public static RedisClient buildRedisClient(Set<HostAndPort> hostAndPorts) {
        return buildRedisClient(hostAndPorts.iterator().next());
    }

    public static RedisClient buildRedisClient(String seed, String requirePass) {
        Set<HostAndPort> hostAndPorts = buildHostAndPortsFromSeed(seed);
        RedisURI redisURI = new RedisURI(hostAndPorts, requirePass);
        return buildRedisClient(redisURI);
    }

    /**
     * xxx,xxx,xxx => set
     * @param seed
     * @return
     */
    private static Set<HostAndPort> buildHostAndPortsFromSeed(String seed) {
        String[] seedArr = StringUtil.splitByCommas(seed);
        Set<HostAndPort> hostAndPorts = new HashSet<>(seedArr.length);
        for (String node : seedArr) {
            String[] hostAndPortArr = StringUtil.splitByColon(node.trim());
            hostAndPorts.add(new HostAndPort(hostAndPortArr[0], Integer.parseInt(hostAndPortArr[1])));
        }
        return hostAndPorts;
    }
}

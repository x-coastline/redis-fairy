package org.coastline.fairy.client.model;

import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.HostAndPort;

import java.util.Set;

/**
 * @author Jay.H.Zou
 * @date 2020/10/31
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RedisURI {

    /**
     * Default timeout: 60 sec
     */
    public static final int DEFAULT_TIMEOUT = 60;

    /**
     * default client name
     */
    public static String DEFAULT_CLIENT_NAME = "fairy-client";

    private Set<HostAndPort> hostAndPortSet;

    private int database;

    private String clientName = DEFAULT_CLIENT_NAME;

    private String sentinelMasterId;

    private String password;

    private int timeout = DEFAULT_TIMEOUT;

    public RedisURI(String host, int port) {
        this(new HostAndPort(host, port), null);
    }

    public RedisURI(String host, int port, String password) {
        this(new HostAndPort(host, port), password);
    }

    public RedisURI(HostAndPort hostAndPort, String password) {
        this(Sets.newHashSet(hostAndPort), password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, String password) {
        this(hostAndPortSet, 0, null, password);
    }

    public RedisURI(Set<HostAndPort> hostAndPortSet, int database, String sentinelMasterId, String password) {
        this.hostAndPortSet = hostAndPortSet;
        this.database = database;
        this.clientName = DEFAULT_CLIENT_NAME;
        this.sentinelMasterId = sentinelMasterId;
        this.password = password;
    }

}
